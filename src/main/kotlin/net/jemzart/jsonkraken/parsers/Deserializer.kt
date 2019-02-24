package net.jemzart.jsonkraken.parsers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.helpers.isDecimal
import net.jemzart.jsonkraken.helpers.isHexadecimal
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete
import net.jemzart.jsonkraken.helpers.isWhiteSpace
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.wrappers.BoundedString
import normalize

internal class Deserializer constructor(raw: String) {
	private val raw = BoundedString(raw)
	private val last = raw.length
	private var start = 0
	private val first get() = raw[start]

	private val errorLocalization: String get() = "at character $start"

	private val errorPreview: String
		get() {
			val offsetBack = start - PREVIEW_OFFSET_BACK
			val offsetForward = start + PREVIEW_OFFSET_FORWARD
			val leftHorizon = offsetBack >= 0
			val rightHorizon = offsetForward <= last
			var left = raw.substring(if (leftHorizon) offsetBack else 0, start)
			var right = raw.substring(start, if (rightHorizon) offsetForward else last)
			left = (if (leftHorizon) ".. " else "") + left
			right += if (rightHorizon) " .." else ""
			val arrow = "^".padStart(left.length + 1)
			return (left + right + "\n" + arrow)
		}

	private companion object {
		const val PREVIEW_OFFSET_BACK = 20
		const val PREVIEW_OFFSET_FORWARD = 20

		const val PARSING_TRUE = "parsing true"
		const val PARSING_FALSE = "parsing false"
		const val PARSING_NULL = "parsing null"
		const val PARSING_NUMBER = "parsing number"
		const val PARSING_STRING = "parsing string"
		const val PARSING_OBJECT = "parsing object"
		const val PARSING_ARRAY = "parsing array"
		const val VERIFYING_END_OF_PARSE = "verifying end of parse"
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
		validateEquality(raw[++start], 'r', PARSING_TRUE)
		validateEquality(raw[++start], 'u', PARSING_TRUE)
		validateEquality(raw[++start], 'e', PARSING_TRUE)
		advance() //skip true
		return true
	}

	private fun deserializeFalse(): Boolean {
		validateEquality(raw[++start], 'a', PARSING_FALSE)
		validateEquality(raw[++start], 'l', PARSING_FALSE)
		validateEquality(raw[++start], 's', PARSING_FALSE)
		validateEquality(raw[++start], 'e', PARSING_FALSE)
		advance() //skip false
		return false
	}

	private fun deserializeNull(): Nothing? {
		validateEquality(raw[++start], 'u', PARSING_NULL)
		validateEquality(raw[++start], 'l', PARSING_NULL)
		validateEquality(raw[++start], 'l', PARSING_NULL)
		advance() //skip null
		return null
	}

	private fun deserializeString(): String {
		advance(trim = false) //skip "
		val valueStart = start
		while (true) {
			if (first == '\\') {
				advance(trim = false) // skip \

				if (first == 'u') {
					validateIsHexadecimal(raw[++start], PARSING_STRING)
					validateIsHexadecimal(raw[++start], PARSING_STRING)
					validateIsHexadecimal(raw[++start], PARSING_STRING)
					validateIsHexadecimal(raw[++start], PARSING_STRING)
					advance(trim = false) //skip uFFFF
				} else {
					validateInclusion(first, Escapable.monoChars, PARSING_STRING)
					advance(trim = false) //skip 1 char
				}
			} else if (first == '"') {
				val value = raw.substring(valueStart, start)
				advance() //skip "
				return value
			} else {
				validateExclusion(raw[start], Escapable.whiteSpaceChars, PARSING_STRING)
				validateIsNotISOControlCharacterOtherThanDelete(raw[start], PARSING_STRING)
				advance(trim = false) //skip 1 char
			}
		}
	}

	private fun minus() {
		advance(trim = false) //skip -
		when (first) {
			'0' -> zero()
			in '1'..'9' -> oneToNine()
			else -> validateIsDecimal(first, PARSING_NUMBER)
		}
	}

	private fun dot() {
		advance(trim = false) //skip .
		when (first) {
			in '0'..'9' -> secondDigitLoop()
			else -> validateIsDecimal(first, PARSING_NUMBER)
		}
	}

	private fun e() {
		advance(trim = false) //skip e or E
		if (first == '+' || first == '-') advance(trim = false) //skip + or -
		if (first in '0'..'9') thirdDigitLoop()
		else validateIsDecimal(first, PARSING_NUMBER)
	}

	private fun zero() {
		advance(trim = false) //skip 0
		if (start == last) return
		when (first) {
			'.' -> dot()
			'e', 'E' -> e()
		}
	}

	private fun oneToNine() {
		advance(trim = false) //skip digit
		if (start == last) return
		when (first) {
			'.' -> dot()
			'e', 'E' -> e()
			in '0'..'9' -> firstDigitLoop()
		}
	}

	private tailrec fun firstDigitLoop() {
		advance(trim = false) //skip digit
		if (start == last) return
		when (first) {
			'.' -> dot()
			'e', 'E' -> e()
			in '0'..'9' -> firstDigitLoop()
		}
	}

	private tailrec fun secondDigitLoop() {
		advance(trim = false) //skip digit
		if (start == last) return
		when (first) {
			'e', 'E' -> e()
			in '0'..'9' -> secondDigitLoop()
		}
	}

	private tailrec fun thirdDigitLoop() {
		advance(trim = false) //skip digit
		if (start == last) return
		when (first) {
			in '0'..'9' -> thirdDigitLoop()
		}
	}

	private fun deserializeNumber(): Any {
		val valueStart = start
		when (first) {
			'-' -> minus()
			'0' -> zero()
			in '1'..'9' -> oneToNine()
			else -> validateIsDecimal(first, PARSING_NUMBER)
		}
		val value = raw.substring(valueStart, start).toDouble()
		skipSpaces()
		return value.normalize()
	}

	private fun deserializeObject(): JsonObject {
		val obj = JsonObject()
		advance() //skip '{'

		if (first != '}')
			while (true) {
				validateEquality(first, '\"', PARSING_OBJECT)

				val name = deserializeString()

				validateEquality(first, ':', PARSING_OBJECT)
				advance() //skip :

				obj.uncheckedSet(name, deserializeValue())

				if (first == ',') advance() //skip ,
				else if (first == '}') break
				else validateInclusion(first, arrayOf(',', '}'), PARSING_OBJECT)
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
				else validateInclusion(first, arrayOf(',', ']'), PARSING_ARRAY)
			}
		advance() //skip ']'
		return arr
	}

	internal fun create(): Any? {
		skipSpaces()
		val result = deserializeValue()
		validateEOF() //no text left
		return result
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

	private fun validateEquality(char: Char, expectation: Char, context: String) {
		if (char != expectation)
			throwError(context, "Expected \"$expectation\", found \"$char\".")
	}

	private fun validateIsHexadecimal(char: Char, context: String) {
		if (!char.isHexadecimal())
			throwError(context, "Expected a hexadecimal character, found \"$char\".")
	}

	private fun validateIsDecimal(char: Char, context: String) {
		if (!char.isDecimal())
			throwError(context, "Expected a decimal character, found \"$char\".")
	}

	private fun validateIsNotISOControlCharacterOtherThanDelete(char: Char, context: String) {
		if (char.isISOControlCharacterOtherThanDelete())
			throwError(context, "\"$char\" is invalid in this context.")
	}

	private fun validateEOF() {
		if (start != last)
			throwError(VERIFYING_END_OF_PARSE,
				"Invalid characters have been found after the end of the outermost json structure." +
					"Should they be removed, the parse would succeed.")
	}

	private fun validateInclusion(char: Char, expectations: Array<Char>, context: String) {
		for (expectation in expectations)
			if (char == expectation) return
		val arr = JsonArray(expectations).toJsonString()
		throwError(context, "Expected one of $arr, found \"$char\".")
	}

	private fun validateExclusion(char: Char, expectations: Array<Char>, context: String) {
		for (expectation in expectations)
			if (char == expectation) {
				val arr = JsonArray(expectations).toJsonString()
				throwError(context, "None of $arr expected, found \"$char\".")
			}
	}

	private fun throwError(context: String, detail: String) {
		val message =
			"\nError $errorLocalization while $context." +
				"\n$detail" +
				"\n$errorPreview"
		throw TokenExpectationException(message)
	}
}