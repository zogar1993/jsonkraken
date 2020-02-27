package net.jemzart.jsonkraken.deserializer

import net.jemzart.jsonkraken.JsonValue
import net.jemzart.jsonkraken.deserializer.deserializers.deserializeValue
import net.jemzart.jsonkraken.deserializer.throws.throwError
import net.jemzart.jsonkraken.deserializer.validators.validateEOF
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.deserializer.validators.validateIsNotEndOfString
import net.jemzart.jsonkraken.helpers.isWhiteSpace

@PublishedApi
internal class Deserializer(val raw: String) {
	val last = raw.length
	var index = 0; private set

	@PublishedApi
	internal fun create(): JsonValue {
		skipWhiteSpaces()
		if (isAtEnd()) throwError(BLANK_RAW_STRING)
		val result = deserializeValue()
		skipWhiteSpaces()
		validateEOF() //no text should be left
		return result
	}

	fun isAtEnd() = index == last
	fun current() = raw[index]
	fun advance() = index++

	fun peek(): Char {
		validateIsNotEndOfString()
		return raw[index]
	}

	fun next(): Char {
		validateIsNotEndOfString()
		return raw[index++]
	}

	fun consume(char: Char) {
		validateEquality(next(), char)
	}

	fun match(char: Char): Boolean {
		validateIsNotEndOfString()
		val result = raw[index] == char
		if (result) index++
		return result
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

	companion object {
		internal const val BLANK_RAW_STRING = "Blank text is not a valid JSON representation."
	}
}