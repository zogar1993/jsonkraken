package net.jemzart.jsonkraken.deserializer

import net.jemzart.jsonkraken.deserializer.deserializers.*
import net.jemzart.jsonkraken.deserializer.validators.validateEOF
import net.jemzart.jsonkraken.deserializer.validators.throwError
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.helpers.isWhiteSpace
import net.jemzart.jsonkraken.values.JsonValue
//TODO Restore advance and advancePeeking to prevent redundant operations
@PublishedApi
internal class Deserializer(val raw: String) {
	val last = raw.length
	var index = 0; private set

	@PublishedApi
	internal fun create(): JsonValue {
		skipWhiteSpaces()
		if (isAtEnd()) throwError("Blank text is not a valid JSON representation.")
		val result = deserializeValue()
		skipWhiteSpaces()
		validateEOF() //no text should be left
		return result
	}

	fun deserializeValue(): JsonValue {
		return when (peek()) {
			'{' -> deserializeObject()
			'[' -> deserializeArray()
			'\"' -> deserializeString()
			't' -> deserializeTrue()
			'f' -> deserializeFalse()
			'n' -> deserializeNull()
			in '0'..'9', '-' -> deserializeNumber()
			else -> throwError("No JSON token starts with '${peek()}'.")
		}
	}

	fun isAtEnd() = index == last

	fun peek(): Char {
		if (isAtEnd()) throwError("Premature end of String")
		return raw[index]
	}

	fun advance(): Char {
		if (isAtEnd()) throwError("Premature end of String")
		return raw[index++]
	}

	fun consume(char: Char) {
		validateEquality(advance(), char)
	}

	fun skipWhiteSpaces() {
		for (i in index until last) {
			if (!raw[i].isWhiteSpace()) {
				index = i
				return
			}
		}
		index = last
	}
}