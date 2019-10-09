package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.validateEquality(char: Char, expectation: Char) {
	if (char != expectation)
		throwError("Expected \"$expectation\", found \"$char\".")
}