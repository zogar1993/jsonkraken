package jemzart.kjson.parsers

import jemzart.kjson.*
import jemzart.kjson.helpers.isHexa
import jemzart.kjson.helpers.isISOControlCharacterOtherThanDelete
import jemzart.kjson.helpers.isWhiteSpace
import jemzart.kjson.helpers.isWhiteSpaceOtherThanSpace
import jemzart.kjson.values.*

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
			'{' -> createObject()
			'[' -> createArray()
			'\"' -> {
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
				value
			}
			't' -> {
				assert(raw[start + 1] == 'r')
				assert(raw[start + 2] == 'u')
				assert(raw[start + 3] == 'e')
				start += 4 //skip true
				true
			}
			'f' -> {
				assert(raw[start + 1] == 'a')
				assert(raw[start + 2] == 'l')
				assert(raw[start + 3] == 's')
				assert(raw[start + 4] == 'e')
				start += 5 //skip false
				false
			}
			'n' -> {
				assert(raw[start + 1] == 'u')
				assert(raw[start + 2] == 'l')
				assert(raw[start + 3] == 'l')
				start += 4 //skip true
				null
			}
			in digits -> {
				createNumber()
			}
			else -> throw UnsupportedOperationException()
		}
	}

	private fun createNumber(): Any {
		val end = fromStartIndexOf { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val literal = raw.substring(start, end)

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
			literal.toDouble()
		else literal.toInt()
	}

	private fun createObject(): JsonObject {
		val obj = JsonObject()
		start += 1 //skip '{'
		skipSpaces()
		if (first == '}')
			start += 1 //skip '}'
		else
			while (true) {
				start += 1//skip "
				val end = fromStartIndexOf('\"')
				val result = raw.substring(start, end)
				start = end + 1 //skip "
				val key = result

				skipSpaces()
				assert(first == ':')
				start += 1//skip :

				skipSpaces()
				obj[key] = extractJsonValue()

				skipSpaces()
				val delimiter = first
				start += 1//skip , or }

				if (delimiter == ',') skipSpaces()
				else if (delimiter == '}') break
				else throw IllegalStateException()
			}
		return obj
	}


	private fun createArray(): JsonArray {
		val arr = jsonArray()
		start += 1 //skip '['
		skipSpaces()
		if (first == ']')
			start += 1 //skip ']'
		else
			while (true) {
				skipSpaces()
				val jsonValue = extractJsonValue()
				arr.add(jsonValue)

				skipSpaces()
				val delimiter = first
				start += 1//skip , or ]

				if (delimiter == ',') skipSpaces()
				else if (delimiter == ']') break
				else throw IllegalStateException()
			}
		return arr
	}

	internal fun create(): Any? {
		skipSpaces()
		val result = extractJsonValue()
		skipSpaces()
		assert(start == last)//no text left
		return result
	}

	fun fromStartIndexOf(occurrence: (Char) -> Boolean): Int {
		for (i in start until last)
			if (occurrence(raw[i]))
				return i
		return last
	}

	fun fromStartIndexOf(char: Char): Int {
		for (i in start until last)
			if (raw[i] == char)
				return i
		return -1
	}

	fun skipSpaces() {
		for (i in start until last) {
			if (!raw[i].isWhiteSpace()) {
				start = i
				return
			}
		}
	}
}