package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.errors.throwExpectationFailed
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.values.JsonArray

internal fun Deserializer.deserializeArray(): JsonArray {
	skipWhiteSpaces()

	if (match(']')) return JsonArray()

	val arr = JsonArray()
	while (true) {
		skipWhiteSpaces()
		deserializeArrayItem(arr)
		skipWhiteSpaces()

		if (match(',')) continue
		if (match(']')) return arr
		throwExpectationFailed(current(), arrayOf(',', ']'))
	}
}

private fun Deserializer.deserializeArrayItem(arr: JsonArray) {
	val item = deserializeValue()
	arr.list.add(item)
}