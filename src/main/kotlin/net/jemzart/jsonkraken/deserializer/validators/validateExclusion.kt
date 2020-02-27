package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwError

internal fun Deserializer.validateExclusion(char: Char, expectations: Array<Char>) {
	for (expectation in expectations)
		if (char == expectation) {
			val arr = JsonArray(expectations).toString()
			throwError("None of $arr expected, found \"$char\".")
		}
}