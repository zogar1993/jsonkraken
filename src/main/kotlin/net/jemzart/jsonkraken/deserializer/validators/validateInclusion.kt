package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.errors.throwExpectationFailed

internal fun Deserializer.validateInclusion(char: Char, expectations: Array<Char>) {
	for (expectation in expectations)
		if (char == expectation) return
	throwExpectationFailed(char, expectations)
}