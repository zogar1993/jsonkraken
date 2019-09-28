package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonArray

internal fun Deserializer.validateExclusion(char: Char, expectations: Array<Char>, context: String) {
	for (expectation in expectations)
		if (char == expectation) {
			val arr = JsonArray(expectations).toString()
			throwError(context, "None of $arr expected, found \"$char\".")
		}
}