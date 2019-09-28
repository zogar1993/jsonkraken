package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.validateEquality(char: Char, expectation: Char, context: String) {
		if (char != expectation)
			throwError(context, "Expected \"$expectation\", found \"$char\".")
	}