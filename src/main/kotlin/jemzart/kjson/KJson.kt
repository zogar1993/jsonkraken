package jemzart.kjson

import jemzart.kjson.helpers.isWhiteSpace
import jemzart.kjson.helpers.isWhiteSpaceOtherThanSpace
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

class KJson internal constructor(private val string: String) {
	private val last = string.length

	companion object {
		internal val jsonTrue = JsonTrue()
		internal val jsonFalse = JsonFalse()
		internal val jsonNull = JsonNull()
		private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
		private val oneCharEscaped = arrayOf('\"', '\\', '/', 'b', 'f', 'n', 'r', 't')
		private val hexas = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
	}

	private fun extractString(): String {
		start += 1//skip "
		beforeIfAny { it == '\"' }
		val result = stringui()
		start = end + 1 //skip "
		end = last
		return result
	}

	private fun extractJsonValue(): JsonValue {
		return when (next) {
			'{' -> createObject()
			'[' -> createArray()
			'\"' -> {
				start += 1//skip "
				val start = start
				while (true) {
					var index = indexOf { it == '\\' || it == '"' }
					if (string[index] == '\\') {
						index++ // skip \
						val escaped = string[index]
						if (escaped in oneCharEscaped)
							index++ // skip 1 char
						else if (escaped == 'u') {
							assert(string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(string[++index].toUpperCase() in hexas) // skip 1 hexa
							assert(string[++index].toUpperCase() in hexas) // skip 1 hexa
						} else throw UnsupportedOperationException()
						this.start = index
					} else {
						this.start = start
						end = index
						break
					}
				}
				val value = stringui()
				for (char in value) {
					assert(!char.isWhiteSpaceOtherThanSpace())
//					val codePoint = value.toInt()
//					assert(!(codePoint <= 0x9F && (codePoint > 0x7F || codePoint.ushr(5) == 0)))
					if (char.toInt() != 0x7F)
						assert(!char.isISOControl())
				}
				this.start = end + 1 //skip "
				end = last

				JsonString(value)
			}
			't' -> {
				assert(string[start + 1] == 'r')
				assert(string[start + 2] == 'u')
				assert(string[start + 3] == 'e')
				start += 4 //skip true
				jsonTrue
			}
			'f' -> {
				assert(string[start + 1] == 'a')
				assert(string[start + 2] == 'l')
				assert(string[start + 3] == 's')
				assert(string[start + 4] == 'e')
				start += 5 //skip false
				jsonFalse
			}
			'n' -> {
				assert(string[start + 1] == 'u')
				assert(string[start + 2] == 'l')
				assert(string[start + 3] == 'l')
				start += 4 //skip true
				jsonNull
			}
			in digits -> {
				createNumber()
			}
			else -> throw UnsupportedOperationException()
		}
	}

	private fun createNumber(): JsonLiteral {
		beforeIfAny { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val literal = stringui()

		val negative = literal[0] == '-'
		if (negative) assert(literal[1] != '.')
		if (literal != "0" && literal != "-0")
			if (literal[if (negative) 1 else 0] == '0')// is not zero but starts with zero
				assert(literal.contains('.') || literal.contains('e', ignoreCase = true))
		val indexOfDot = literal.indexOf('.')
		assert(literal.last() != '.')
		assert(literal[indexOfDot + 1].toLowerCase() != 'e')
		start = end
		end = last

		return if (literal.contains('.') || literal.contains('e', ignoreCase = true))
			JsonDouble(literal.toDouble())
		else JsonInteger(literal.toInt())
	}

	private fun createObject(): JsonObject {
		val attributes = LinkedHashMap<String, JsonValue>()
		start += 1 //skip '{'
		skipSpaces()
		if (next == '}')
			start += 1 //skip '}'
		else
			while (true) {
				val key = extractString()

				skipSpaces()
				assert(next == ':')
				start += 1//skip :

				skipSpaces()
				val jsonValue: JsonValue = extractJsonValue()
				attributes[key] = jsonValue

				skipSpaces()
				val delimiter = next
				start += 1//skip , or }

				if (delimiter == ',') skipSpaces()
				else if (delimiter == '}') break
				else throw IllegalStateException()
			}
		return JsonObject(attributes)
	}


	private fun createArray(): JsonArray {
		val items = LinkedList<JsonValue>()
		start += 1 //skip '['
		skipSpaces()
		if (next == ']')
			start += 1 //skip ']'
		else
			while (true) {
				skipSpaces()
				val jsonValue = extractJsonValue()
				items.add(jsonValue)

				skipSpaces()
				val delimiter = next
				start += 1//skip , or ]

				if (delimiter == ',') skipSpaces()
				else if (delimiter == ']') break
				else throw IllegalStateException()
			}
		return JsonArray(items)
	}

	internal fun create(): JsonValue {
		skipSpaces()
		val result = extractJsonValue()
		skipSpaces()
		assert(start == end)
		return result
	}

	var start = 0
	var end = string.length
	val next get() = string[start]
	operator fun get(i: Int) = string[start + i]

	fun beforeIfAny(occurrence: (Char) -> Boolean) {
		for (i in start until end)
			if (occurrence(string[i])) {
				end = i
				return
			}
	}

	fun stringui(): String {
		return string.substring(start, end)
	}

	fun skipSpaces() {
		for (i in start until end) {
			if (!string[i].isWhiteSpace()) {
				start = i
				return
			}
		}
		end = start
	}

	fun indexOf(occurrence: (Char) -> Boolean): Int {
		for (i in start until end)
			if (occurrence(string[i])) {
				return i
			}
		return -1
	}
}