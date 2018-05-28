package jemzart.jsonkraken.parsers

import jemzart.jsonkraken.helpers.isHexa
import jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete
import jemzart.jsonkraken.helpers.isWhiteSpace
import jemzart.jsonkraken.helpers.isWhiteSpaceOtherThanSpace
import jemzart.jsonkraken.values.JsonArray
import jemzart.jsonkraken.values.JsonObject

class StringToObjectParser internal constructor(private val raw: String) {
	private val last = raw.length
	private var start = 0
	private val first get() = raw[start]

	companion object {
		private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
		private val oneCharEscaped = arrayOf('\"', '\\', '/', 'b', 'f', 'n', 'r', 't')
	}

	private fun extractJsonValue(): Any? {
		return when (first) {
			'{' -> deserializeObject()
			'[' -> deserializeArray()
			'\"' -> deserializeString()
			't' -> 	deserializeTrue()
			'f' -> deserializeFalse()
			'n' -> deserializeNull()
			in digits -> deserializeNumber()
			else -> throw UnsupportedOperationException()
		}
	}

	private fun deserializeTrue(): Boolean {
		assert(raw[start + 1] == 'r')
		assert(raw[start + 2] == 'u')
		assert(raw[start + 3] == 'e')
		advance(4) //skip true
		return true
	}

	private fun deserializeFalse(): Boolean {
		assert(raw[start + 1] == 'a')
		assert(raw[start + 2] == 'l')
		assert(raw[start + 3] == 's')
		assert(raw[start + 4] == 'e')
		advance(5) //skip false
		return false
	}

	private fun deserializeNull(): Nothing? {
		assert(raw[start + 1] == 'u')
		assert(raw[start + 2] == 'l')
		assert(raw[start + 3] == 'l')
		advance(4) //skip true
		return null
	}

	private fun deserializeString(): String {
		start += 1//skip "
		val memoryStart = start
		val end: Int
		while (true) {
			var index = fromStartIndexOf { it == '\\' || it == '"' }
			if (raw[index] == '\\') {
				index++ // skip \
				val escaped = raw[index]
				if (escaped in oneCharEscaped)
					index++ // skip 1 char
				else if (escaped == 'u') {
					assert(raw[index + 1].isHexa())
					assert(raw[index + 2].isHexa())
					assert(raw[index + 3].isHexa())
					assert(raw[index + 4].isHexa())
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
			assert(!raw[i].isWhiteSpaceOtherThanSpace())
			assert(!raw[i].isISOControlCharacterOtherThanDelete())
		}

		val value = raw.substring(start, end)
		this.start = end + 1 //skip "
		return value
	}

	private fun deserializeNumber(): Any {
		val end = fromStartIndexOf { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val literal = raw.substring(start, end)
		advance(literal.length) //skip value

		var index = 0
		if (literal[index] == '-') index++ //skip -
		if (literal[index] == '0'){
			if (literal.length == index + 1) return 0 //no more to read
			index++ //skip 0
			assert(literal[index] == '.')
		}
		else
			while (true){
				assert(literal[index].isDigit())
				if (literal.length == index + 1) return literal.toInt() //no more to read
				index++ //skip digit
				if (literal[index] == '.') break
			}
		index++ //skip .
		assert(literal[index].isDigit())
		index++ //skip first decimal digit
		var foundE = false
		while (true){
			if (literal.length == index + 1) return literal.toDouble() //no more to read
			if (!foundE)
				if (literal[index] == 'e' || literal[index] == 'E'){
					index++ //skip e or E
					foundE = true
					if (literal[index] == '+' || literal[index] == '-') index++ //skip + or -
				}
			assert(literal[index].isDigit())
			index++ //skip digit
		}
	}

	private fun deserializeObject(): JsonObject {
		val obj = JsonObject()
		advance() //skip '{'
		if (first != '}')
			while (true) {
			assert(first == '\"')
			start += 1//skip "

			val end = fromStartIndexOf('\"')
			val key = raw.substring(start, end)
			advance(key.length, false) //skip key
			advance() //skip "

			assert(first == ':')
			advance() //skip :

			obj[key] = extractJsonValue()

			skipSpaces()

			if (first == ',') advance() //skip ,
			else if (first == '}') break
			else throw IllegalStateException()
			}
		advance() //skip '}'
		return obj
	}


	private fun deserializeArray(): JsonArray {
		val arr = JsonArray()
		advance() //skip '['
		if (first != ']')
			while (true) {
				val jsonValue = extractJsonValue()
				arr.add(jsonValue)

				skipSpaces()

				if (first == ',') advance() //skip ','
				else if (first == ']') break
				else throw IllegalStateException()
			}
		advance() //skip ']'
		return arr
	}

	internal fun create(): Any? {
		skipSpaces()
		val result = extractJsonValue()
		skipSpaces()
		assert(start == last)//no text left
		return result
	}

	private inline fun fromStartIndexOf(occurrence: (Char) -> Boolean): Int {
		for (i in start until last)
			if (occurrence(raw[i]))
				return i
		return last
	}

	private fun fromStartIndexOf(char: Char): Int {
		for (i in start until last)
			if (raw[i] == char)
				return i
		return -1
	}

	private fun skipSpaces() {
		for (i in start until last) {
			if (!raw[i].isWhiteSpace()) {
				start = i
				return
			}
		}
	}

	private inline fun advance(value: Int = 1, advanceFinalWhiteSpaces: Boolean = true){
		start += value
		if (advanceFinalWhiteSpaces) skipSpaces()
	}
}