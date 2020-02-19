package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.throwExpectationFailed(char: Char, expected: Array<Char>) {
	val expectation = "[${expected.joinToString(",")}]"
	throwError("Expected one of ${expectation}, found \"$char\".")
}