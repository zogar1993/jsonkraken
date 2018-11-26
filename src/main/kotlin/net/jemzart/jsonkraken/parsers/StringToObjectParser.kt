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

internal class StringToObjectParser constructor(private val raw: String) {
	private val last = raw.length
	private var start = 0
	private inline val first get() = raw[start]

    private val parsingTrue = { "parsing true" }
    private val parsingFalse = { "parsing false" }
    private val parsingNull = { "parsing null" }
    private val parsingNumber = { "parsing number" }
    private val parsingString = { "parsing string" }
    private val parsingObject = { "parsing object" }
    private val parsingArray = { "parsing array" }
    private val verifyingEndOfParse  = { "verifying end of parse" }

	private val errorLocalization: String get() = "at character $start"

	private val errorPreview: String get() {
		val offsetBack = start - PREVIEW_OFFSET_BACK
		val offsetForward = start + PREVIEW_OFFSET_FORWARD
		val leftHorizon = offsetBack >= 0
		val rightHorizon = offsetForward <= raw.length
		var left = raw.substring(if (leftHorizon) offsetBack else 0, start)
		var right = raw.substring(start, if (rightHorizon) offsetForward else raw.length)
		left = (if (leftHorizon) ".. " else "") + left
		right += if (rightHorizon) " .." else ""
		val arrow = "^".padStart(left.length + 1)
		return (left + right + "\n" + arrow)
	}

	companion object {
		const val PREVIEW_OFFSET_BACK = 20
		const val PREVIEW_OFFSET_FORWARD = 20
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
		validateEquality(raw[++start], 'r', parsingTrue)
		validateEquality(raw[++start], 'u', parsingTrue)
		validateEquality(raw[++start], 'e', parsingTrue)
		advance() //skip true
		return true
	}

	private fun deserializeFalse(): Boolean {
		validateEquality(raw[++start], 'a', parsingFalse)
		validateEquality(raw[++start], 'l', parsingFalse)
		validateEquality(raw[++start], 's', parsingFalse)
		validateEquality(raw[++start], 'e', parsingFalse)
		advance() //skip false
		return false
	}

	private fun deserializeNull(): Nothing? {
		validateEquality(raw[++start], 'u', parsingNull)
		validateEquality(raw[++start], 'l', parsingNull)
		validateEquality(raw[++start], 'l', parsingNull)
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
					validateIsHexadecimal(raw[++start], parsingString)
					validateIsHexadecimal(raw[++start], parsingString)
					validateIsHexadecimal(raw[++start], parsingString)
					validateIsHexadecimal(raw[++start], parsingString)
					advance(trim = false) //skip uFFFF
				} else {
					validateInclusion(first, Escapable.monoChars, parsingString)
					advance(trim = false) //skip 1 char
				}
			} else if (first == '"') {
				val value = raw.substring(valueStart, start)
				advance() //skip "
				return value
			} else {
				validateExclusion(raw[start], Escapable.whiteSpaceChars, parsingString)
				validateIsISOControlCharacterOtherThanDelete(raw[start], parsingString)
				advance(trim = false) //skip 1 char
			}
		}
	}

	private fun deserializeNumber(): Any {
		val end = fromStartIndexOf { it.isWhiteSpace() || it == '}' || it == ']' || it == ',' }
		val valueStart = start

		if (first == '-') advance(trim = false) //skip -
		if (first == '0') {
			advance() //skip 0
			if (start == end) {
				skipSpaces()
				return 0.0 //no more to read
			} else
				validateEquality(first, '.', parsingNumber)
		} else
			while (true) {
				validateIsDecimal(first, parsingNumber)
				advance(trim = false)//skip digit
				if (start == end) {
					skipSpaces()
					return raw.substring(valueStart, end).toDouble()//no more to read
				}
				if (first == '.') break
			}
		advance(trim = false) //skip .
		validateIsDecimal(first, parsingNumber)
		advance(trim = false) //skip first decimal digit
		if (start == end) {
			skipSpaces()
			val number = raw.substring(valueStart, end).toDouble()
			return if (number == 0.0) 0.0 else number //turns -0.0 into 0.0 to prevent boxing issues
		} //no more to read
		var foundE = false
		while (true) {
			if (!foundE)
				if (first == 'e' || first == 'E') {
					advance(trim = false) //skip e or E
					foundE = true
					if (first == '+' || first == '-') advance() //skip + or -
				}
			validateIsDecimal(first, parsingNumber)
			advance(trim = false)//skip decimal
			if (start == end) {
				skipSpaces()
				return raw.substring(valueStart, end).toDouble() //no more to read
			}
			advance(trim = false) //skip digit
		}
	}

	private fun deserializeObject(): JsonObject {
		val obj = JsonObject()
		advance() //skip '{'

		if (first != '}')
			while (true) {
				validateEquality(first, '\"', parsingObject)

				val name = deserializeString()

				validateEquality(first, ':', parsingObject)
				advance() //skip :

				obj.uncheckedSet(name, deserializeValue())

				if (first == ',') advance() //skip ,
				else if (first == '}') break
				else validateInclusion(first, arrayOf(',', '}'), parsingObject)
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
				else validateInclusion(first, arrayOf(',', ']'), parsingArray)
			}
		advance() //skip ']'
		return arr
	}

	internal fun create(): Any? {
		skipSpaces()
		val result = deserializeValue()
		validateEOF(verifyingEndOfParse)//no text left
		return result
	}

	private inline fun fromStartIndexOf(occurrence: (Char) -> Boolean): Int {
		for (i in start until last)
			if (occurrence(raw[i]))
				return i
		return last
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

	private fun validateEquality(char: Char, expectation: Char, context: () -> String) {
		validateToken(char == expectation, context) { "Expected \"$expectation\", found \"$char\"." }
	}

	private fun validateIsHexadecimal(char: Char, context: () -> String) {
		validateToken(char.isHexadecimal(), context) { "Expected a hexadecimal character, found \"$char\"." }
	}

	private fun validateIsDecimal(char: Char, context: () -> String) {
		validateToken(char.isDecimal(), context) { "Expected a decimal character, found \"$char\"." }
	}

	private fun validateIsISOControlCharacterOtherThanDelete(char: Char, context: () -> String) {
		validateToken(!char.isISOControlCharacterOtherThanDelete(), context)
		{ "\"$char\" is invalid in this context." }
	}

	private fun validateEOF(context: () -> String) {
		validateToken(start == last, context)
		{ "Invalid characters have been found after the end of the outermost json structure." +
			"Should they be removed, the parse would succeed." }
	}

	private fun validateInclusion(char: Char, expectations: Array<Char>, context: () -> String) {
		for (expectation in expectations)
			if (char == expectation) return
		val arr = JsonArray(expectations).toJsonString()
		validateToken(false, context) { "Expected one of $arr, found \"$char\"." }
	}

	private fun validateExclusion(char: Char, expectations: Array<Char>, context: () -> String) {
		for (expectation in expectations)
			if (char == expectation) {
				val arr = JsonArray(expectations).toJsonString()
				validateToken(false, context) { "None of $arr expected, found \"$char\"." }
			}
	}

	private inline fun validateToken(value: Boolean, context: () -> String, lazyMessage: () -> String) {
		if (!value) {
			val message =
				"\nError $errorLocalization while ${context()}." +
				"\n${lazyMessage()}" +
				"\n$errorPreview"
			throw TokenExpectationException(message)
		}
	}
}