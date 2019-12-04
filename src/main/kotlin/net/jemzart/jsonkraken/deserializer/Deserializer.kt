package net.jemzart.jsonkraken.deserializer

import net.jemzart.jsonkraken.deserializer.deserializers.deserializeValue
import net.jemzart.jsonkraken.deserializer.errors.throwError
import net.jemzart.jsonkraken.deserializer.validators.validateEOF
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.helpers.isWhiteSpace
import net.jemzart.jsonkraken.JsonValue

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

	fun isAtEnd() = index == last

	fun peek(): Char {
		if (isAtEnd()) throwError("Premature end of String")
		return raw[index]
	}

	fun current(): Char {
		return raw[index]
	}

	fun advance() {
		index++
	}

	fun next(): Char {
		if (isAtEnd()) throwError("Premature end of String")
		return raw[index++]
	}

	fun consume(char: Char) {
		validateEquality(next(), char)
	}

	fun match(char: Char): Boolean {
		if (isAtEnd()) throwError("Premature end of String")
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
}