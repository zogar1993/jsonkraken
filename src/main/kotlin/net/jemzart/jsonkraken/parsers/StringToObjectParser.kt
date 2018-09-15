package net.jemzart.jsonkraken.parsers

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.helpers.isDecimal
import net.jemzart.jsonkraken.helpers.isHexadecimal
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete
import net.jemzart.jsonkraken.helpers.isWhiteSpace
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class StringToObjectParser internal constructor(private val raw: String) {
	private val last = raw.length
	private var start = 0
	private inline val first get() = raw[start]

	private val errorLocalization: String get() = "Error while parsing token which starts at character $start.\n" +
		"A preview of the error has been made.\n" +
		"Invalid token starts after the pipe | (which is not in the original file).\n" +
		errorPreview
	private val errorPreview: String get() {
		val offsetBack = start - PREVIEW_OFFSET_BACK
		val offsetForward = start + PREVIEW_OFFSET_FORWARD
		val leftHorizon = offsetBack >= 0
		val rightHorizon = offsetForward <= raw.length
		val left = raw.substring(if (leftHorizon) offsetBack else 0, start)
		val right = raw.substring(start, if (rightHorizon) offsetForward else raw.length)
		return (if (leftHorizon) ".. " else "") +	"$left|$right" + (if (rightHorizon) " .." else "")
	}

	companion object {
		const val PREVIEW_OFFSET_BACK = 20
		const val PREVIEW_OFFSET_FORWARD = 20
		const val SUGGESTION_WRAP_STRINGS_IN_DOUBLE_QUOTES = "Maybe you have a string with missing double quotes?"
		const val EXPECTED_CHARACTER = "Expected one of the following characters: "
	}

	private fun deserializeValue(): Any? {
		return when (first) {
			'{' -> deserializeObject()
			'[' -> deserializeArray()
			'\"' -> deserializeString()
			't' -> deserializeTrue()
			'f' -> deserializeFalse()
			'n' -> deserializeNull()
			else -> deserializeNumber()
		}
	}



	private fun deserializeTrue(): Boolean {
		validateEquality(raw[start + 1], 'r')
		validateEquality(raw[start + 2], 'u')
		validateEquality(raw[start + 3], 'e')
		advance(4) //skip true
		return true
	}

	private fun deserializeFalse(): Boolean {
		validateEquality(raw[start + 1], 'a')
		validateEquality(raw[start + 2], 'l')
		validateEquality(raw[start + 3], 's')
		validateEquality(raw[start + 4], 'e')
		advance(5) //skip false
		return false
	}

	private fun deserializeNull(): Nothing? {
		validateEquality(raw[start + 1], 'u')
		validateEquality(raw[start + 2], 'l')
		validateEquality(raw[start + 3], 'l')
		advance(4) //skip true
		return null
	}

	private fun deserializeString(): String {
		advance(trim = false) //skip "
		val valueStart = start
		while (true) {
			if (first == '\\') {
				advance(trim = false) // skip \

				if (first == 'u') {
					validateIsHexadecimal(raw[start + 1])
					validateIsHexadecimal(raw[start + 2])
					validateIsHexadecimal(raw[start + 3])
					validateIsHexadecimal(raw[start + 4])
					advance(5, false) //skip uFFFF
				} else {
					//TODO validate if array is created everytime, it should not be.
					validateInclusion(first, arrayOf('\"', '\\', '/', 'b', 'f', 'n','r','t'))
					advance(trim = false) //skip 1 char
				}
			} else if (first == '"') {
				val value = raw.substring(valueStart, start)
				advance() //skip "
				return value
			} else {
				//TODO validate if array is created everytime, it should not be.
				validateExclusion(raw[start], arrayOf('\n', '\t', '\r'))
				validateIsISOControlCharacterOtherThanDelete(raw[start])
				advance(trim = false) //skip 1 char
			}
		}
	}

	private fun deserializeNumber(): Any {
		val end = fromStartIndexOf { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val literal = raw.substring(start, end)
		advance(literal.length) //skip value

		var index = 0
		if (literal[index] == '-') index++ //skip -
		if (literal[index] == '0') {
			if (literal.length == index + 1) return 0 //no more to read
			index++ //skip 0
			validateEquality(literal[index], '.')
		} else
			while (true) {
				validateIsDecimal(literal[index])
				if (literal.length == index + 1) return literal.toInt() //no more to read
				index++ //skip digit
				if (literal[index] == '.') break
			}
		index++ //skip .
		validateIsDecimal(literal[index])
		if (literal.length == index + 1) return literal.toDouble() //no more to read
		index++ //skip first decimal digit
		var foundE = false
		while (true) {
			if (!foundE)
				if (literal[index] == 'e' || literal[index] == 'E') {
					index++ //skip e or E
					foundE = true
					if (literal[index] == '+' || literal[index] == '-') index++ //skip + or -
				}
			validateIsDecimal(literal[index])
			if (literal.length == index + 1) return literal.toDouble() //no more to read
			index++ //skip digit
		}
	}

	private fun deserializeObject(): JsonObject {
		val obj = JsonObject()
		advance() //skip '{'

		if (first != '}')
			while (true) {
				validateEquality(first, '\"')
				advance(trim = false)//skip "

				val end = fromStartIndexOf('\"')
				val key = raw.substring(start, end)
				advance(key.length, false) //skip key
				advance() //skip "

				validateEquality(first, ':')
				advance() //skip :

				obj.uncheckedSet(key, deserializeValue())

				if (first == ',') advance() //skip ,
				else if (first == '}') break
				else validateInclusion(first, arrayOf(',', '}'))
			}
		advance() //skip '}'
		return obj
	}


	private fun deserializeArray(): JsonArray {
		val arr = JsonArray()
		advance() //skip '['
		if (first != ']')
			while (true) {
				arr.uncheckedAdd(deserializeValue())

				if (first == ',') advance() //skip ','
				else if (first == ']') break
				else validateInclusion(first, arrayOf(',', ']'))
			}
		advance() //skip ']'
		return arr
	}

	internal fun create(): Any? {
		skipSpaces()
		val result = deserializeValue()
		validateEOF()//no text left
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
		start = last
	}

	@Suppress("NOTHING_TO_INLINE")//Micro optimization on boolean parameter
	private inline fun advance(value: Int = 1, trim: Boolean = true) {
		start += value
		if (trim) skipSpaces()
	}

	private fun validateEquality(char: Char, expectation: Char) {
		validateToken(char == expectation) { "Expected the following character: $expectation." }
	}

	private fun validateIsHexadecimal(char: Char) {
		validateToken(char.isHexadecimal()) { "Expected a hexadecimal character." }
	}

	private fun validateIsDecimal(char: Char) {
		validateToken(char.isDecimal()) { "Expected a decimal character." }
	}

	private fun validateIsISOControlCharacterOtherThanDelete(char: Char) {
		validateToken(!char.isISOControlCharacterOtherThanDelete())
		{ "Did not expect an ISO control character other than the \"Delete\" one." }
	}

	private fun validateEOF() {
		validateToken(start == last)
		{ "Invalid characters have been found after the end of the outermost json structure." +
			"Should they be removed, the parse would succeed." }
	}

	private fun validateInclusion(char: Char, expectations: Array<Char>) {
		for (expectation in expectations)
			if (char == expectation) return
		val arr = JsonArray(expectations).toJsonString()
		validateToken(false) { "Expected one of the following characters: $arr." }
	}

	private fun validateExclusion(char: Char, expectations: Array<Char>) {
		for (expectation in expectations)
			if (char == expectation) {
				val arr = JsonArray(expectations).toJsonString()
				validateToken(false) { "Did not expect to find one of the following characters: $arr." }
			}
	}

	private inline fun validateToken(value: Boolean, lazyMessage: () -> Any) {
		if (!value) {
			val message = "$errorLocalization\n${lazyMessage()}"
			throw TokenExpectationException(message)
		}
	}
}