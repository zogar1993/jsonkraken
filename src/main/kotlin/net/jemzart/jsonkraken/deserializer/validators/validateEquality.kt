package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.errors.throwError

internal fun Deserializer.validateEquality(char: Char, expectation: Char) {
	if (char != expectation)
		throwError("Expected \"$expectation\", found \"$char\".")
}