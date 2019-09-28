package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonArray

internal fun Deserializer.validateInclusion(char: Char, expectations: Array<Char>, context: String) {
		for (expectation in expectations)
			if (char == expectation) return
		val arr = JsonArray(expectations).toString()
		throwError(context, "Expected one of $arr, found \"$char\".")
	}