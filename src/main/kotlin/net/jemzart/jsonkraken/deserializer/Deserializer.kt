package net.jemzart.jsonkraken.deserializer

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.helpers.isDecimal
import net.jemzart.jsonkraken.helpers.isWhiteSpace
import net.jemzart.jsonkraken.deserializer.deserializers.*
import net.jemzart.jsonkraken.deserializer.deserializers.deserializeFalse
import net.jemzart.jsonkraken.deserializer.deserializers.deserializeNull
import net.jemzart.jsonkraken.deserializer.deserializers.deserializeNumber
import net.jemzart.jsonkraken.deserializer.deserializers.deserializeString
import net.jemzart.jsonkraken.values.*

@PublishedApi
internal class Deserializer(val raw: String) {
	private val last = raw.length
	var index = 0; private set

	fun isAtEnd() = index == last
	fun peek(): Char {
		if (isAtEnd()) throw TokenExpectationException("Premature end of String")
		return raw[index]
	}

	fun advance(): Char {
		if (isAtEnd()) throw TokenExpectationException("Premature end of String")
		return raw[index++]
	}

	private val errorLocalization: String get() = "at character $index"

	private val errorPreview: String
		get() {
			val offsetBack = index - PREVIEW_OFFSET_BACK
			val offsetForward = index + PREVIEW_OFFSET_FORWARD
			val leftHorizon = offsetBack >= 0
			val rightHorizon = offsetForward <= last
			var left = raw.substring(if (leftHorizon) offsetBack else 0, index)
			var right = raw.substring(index, if (rightHorizon) offsetForward else last)
			left = (if (leftHorizon) ".. " else "") + left
			right += if (rightHorizon) " .." else ""
			val arrow = "^".padStart(left.length + 1)
			return (left + right + "\n" + arrow)
		}

	private companion object {
		const val PREVIEW_OFFSET_BACK = 20
		const val PREVIEW_OFFSET_FORWARD = 20

		const val VERIFYING_END_OF_PARSE = "verifying end of parse"
	}

	@PublishedApi
	internal fun create(): JsonValue {
		val result = deserializeValue()
		validateEOF() //no text left
		return result
	}

	fun deserializeValue(): JsonValue {
		skipSpaces()
		val value = when (peek()) {
			'{' -> deserializeObject()
			'[' -> deserializeArray()
			'\"' -> deserializeString()
			't' -> deserializeTrue()
			'f' -> deserializeFalse()
			'n' -> deserializeNull()
			else -> deserializeNumber()
		}
		skipSpaces()
		return value
	}

	fun skipSpaces() {
		for (i in index until last) {
			if (!raw[i].isWhiteSpace()) {
				index = i
				return
			}
		}
		index = last
	}

	fun advanceAndTrim() {
		advance()
		skipSpaces()
	}

	fun validateEquality(char: Char, expectation: Char, context: String) {
		if (char != expectation)
			throwError(context, "Expected \"$expectation\", found \"$char\".")
	}

	fun validateIsDecimal(char: Char, context: String) {
		if (!char.isDecimal())
			throwError(context, "Expected a decimal character, found \"$char\".")
	}

	fun validateEOF() {
		if (!isAtEnd())
			throwError(VERIFYING_END_OF_PARSE,
				"Invalid characters have been found after the end of the outermost json structure." +
					"Should they be removed, the parse would succeed.")
	}

	fun validateInclusion(char: Char, expectations: Array<Char>, context: String) {
		for (expectation in expectations)
			if (char == expectation) return
		val arr = JsonArray(expectations).toString()
		throwError(context, "Expected one of $arr, found \"$char\".")
	}

	fun throwError(context: String, detail: String) {
		val message =
			"\nError $errorLocalization while $context." +
				"\n$detail" +
				"\n$errorPreview"
		throw TokenExpectationException(message)
	}
}