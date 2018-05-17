package jemzart.kjson

import jemzart.kjson.values.*
import java.util.*
import kotlin.collections.LinkedHashMap

fun emptyJsonObject() = JsonObject(LinkedHashMap())
fun emptyJsonArray() = JsonArray(mutableListOf())
fun jsonTrue() = KJson.jsonTrue
fun jsonFalse() = KJson.jsonFalse
fun jsonBoolean(value: Boolean) = if (value) KJson.jsonTrue else KJson.jsonFalse
fun jsonNull() = KJson.jsonNull
fun String.toJson(): JsonValue = KJson(this).create()

class KJson internal constructor(raw: String) {
	private val json: Text = Text(raw)
	private val end = raw.length

	companion object {
		internal val jsonTrue = JsonTrue()
		internal val jsonFalse = JsonFalse()
		internal val jsonNull = JsonNull()
		private val whiteChars = arrayOf('\t', '\n', ' ', '\r')
		private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
		private val oneCharEscaped = arrayOf('\"', '\\', '/', 'b', 'f', 'n', 'r', 't')
		private val hexas = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
	}

	private fun extractString(): String {
		json.start += 1//skip "
		json.beforeIfAny { it == '\"' }
		val result = json.toString()
		json.start = json.end + 1 //skip "
		json.end = end
		return result
	}

	private fun extractJsonValue(): JsonValue {
		return when (json.next) {
			'{' -> createObject()
			'[' -> createArray()
			'\"' -> {
				json.start += 1//skip "
				val start = json.start
				while (true) {
					var index = json.indexOf { it == '\\' || it == '"' }
					if (json.string[index] == '\\') {
						index++ // skip \
						val escaped = json.string[index]
						if (escaped in oneCharEscaped)
							index++ // skip 1 char
						else if (escaped == 'u') {
							assert(json.string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(json.string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(json.string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(json.string[++index].toUpperCase() in hexas) // skip 1 hexa
						} else throw UnsupportedOperationException()
						json.start = index
					} else {
						json.start = start
						json.end = index
						break
					}
				}
				val value = json.toString()
				for (char in value) {
					assert(char != '\n')
					assert(char != '\t')
					assert(char != '\r')
//					val codePoint = value.toInt()
//					assert(!(codePoint <= 0x9F && (codePoint > 0x7F || codePoint.ushr(5) == 0)))
					if (char.toInt() != 0x7F)
						assert(!char.isISOControl())
				}
				json.start = json.end + 1 //skip "
				json.end = end

				JsonString(value)
			}
			't' -> {
				json.start += 4
				jsonTrue
			}
			'f' -> {
				json.start += 5
				jsonFalse
			}
			'n' -> {
				json.start += 4
				jsonNull
			}
			in digits -> {
				createNumber()
			}
			else -> throw UnsupportedOperationException()
		}
	}

	private fun createNumber(): JsonLiteral {
		json.beforeIfAny { it in whiteChars || it == '}' || it == ']' || it == ',' }
		val literal = json.toString()

		val negative = literal[0] == '-'
		if (negative) assert(literal[1] != '.')
		if (literal != "0" && literal != "-0")
			if (literal[if (negative) 1 else 0] == '0')// is not zero but starts with zero
				assert(literal.contains('.') || literal.contains('e', ignoreCase = true))
		val indexOfDot = literal.indexOf('.')
		assert(literal.last() != '.')
		assert(literal[indexOfDot + 1].toLowerCase() != 'e')
		json.start = json.end
		json.end = end

		return if (literal.contains('.') || literal.contains('e', ignoreCase = true))
			JsonDouble(literal.toDouble())
		else JsonInteger(literal.toInt())
	}

	private fun createObject(): JsonObject {
		val attributes = LinkedHashMap<String, JsonValue>()
		json.start += 1 //skip '{'
		json.skipSpaces()
		if (json.next == '}')
			json.start += 1 //skip '}'
		else
			while (true) {
				val key = extractString()

				json.skipSpaces()
				assert(json.next == ':')
				json.start += 1//skip :

				json.skipSpaces()
				val jsonValue: JsonValue = extractJsonValue()
				attributes[key] = jsonValue

				json.skipSpaces()
				val delimiter = json.next
				json.start += 1//skip , or }

				if (delimiter == ',') json.skipSpaces()
				else if (delimiter == '}') break
				else throw IllegalStateException()
			}
		return JsonObject(attributes)
	}


	private fun createArray(): JsonArray {
		val items = LinkedList<JsonValue>()
		json.start += 1 //skip '['
		json.skipSpaces()
		if (json.next == ']')
			json.start += 1 //skip ']'
		else
			while (true) {
				json.skipSpaces()
				val jsonValue = extractJsonValue()
				items.add(jsonValue)

				json.skipSpaces()
				val delimiter = json.next
				json.start += 1//skip , or ]

				if (delimiter == ',') json.skipSpaces()
				else if (delimiter == ']') break
				else throw IllegalStateException()
			}
		return JsonArray(items)
	}

	internal fun create(): JsonValue {
		json.skipSpaces()
		val result = extractJsonValue()
		json.skipSpaces()
		assert(json.start == json.end)
		return result
	}
}