package jemzart.kjson

import jemzart.kjson.helpers.isHexa
import jemzart.kjson.helpers.isISOControlCharacterOtherThanDelete
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
	var start = 0
	val next get() = string[start]

	companion object {
		internal val jsonTrue = JsonTrue()
		internal val jsonFalse = JsonFalse()
		internal val jsonNull = JsonNull()
		private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
		private val oneCharEscaped = arrayOf('\"', '\\', '/', 'b', 'f', 'n', 'r', 't')
	}
	private fun extractJsonValue(): JsonValue {
		return when (next) {
			'{' -> createObject()
			'[' -> createArray()
			'\"' -> {
				start += 1//skip "
				val memoryStart = start
				val end: Int
				while (true) {
					var index = indexOf { it == '\\' || it == '"' }
					if (string[index] == '\\') {
						index++ // skip \
						val escaped = string[index]
						if (escaped in oneCharEscaped)
							index++ // skip 1 char
						else if (escaped == 'u') {
							assert(string[index + 1].isHexa())
							assert(string[index + 2].isHexa())
							assert(string[index + 3].isHexa())
							assert(string[index + 4].isHexa())
							index += 5 //skip uFFFF
						} else throw UnsupportedOperationException()
						start = index
					} else {
						start = memoryStart
						end = index
						break
					}
				}

				for (i in start until end) {
					assert(!string[i].isWhiteSpaceOtherThanSpace())
					assert(!string[i].isISOControlCharacterOtherThanDelete())
				}

				val value = stringUpTo(end)
				this.start = end + 1 //skip "
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
		val end = beforeIfAny { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val literal = stringUpTo(end)

		val negative = literal[0] == '-'
		if (negative) assert(literal[1] != '.')
		if (literal != "0" && literal != "-0")
			if (literal[if (negative) 1 else 0] == '0')// is not zero but starts with zero
				assert(literal.contains('.') || literal.contains('e', ignoreCase = true))
		val indexOfDot = literal.indexOf('.')
		assert(literal.last() != '.')
		assert(literal[indexOfDot + 1].toLowerCase() != 'e')
		start = end

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
				start += 1//skip "
				val end = beforeIfAny { it == '\"' }
				val result = stringUpTo(end)
				start = end + 1 //skip "
				val key = result

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
		assert(start == last)//no text left
		return result
	}

	fun beforeIfAny(occurrence: (Char) -> Boolean): Int {
		for (i in start until last)
			if (occurrence(string[i]))
				return i
		return last
	}

	fun stringUpTo(end: Int): String {
		return string.substring(start, end)
	}

	fun skipSpaces() {
		for (i in start until last) {
			if (!string[i].isWhiteSpace()) {
				start = i
				return
			}
		}
	}

	fun indexOf(occurrence: (Char) -> Boolean): Int {
		for (i in start until last)
			if (occurrence(string[i])) {
				return i
			}
		return -1
	}
}