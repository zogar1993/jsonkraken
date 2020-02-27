package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwExpectationFailed

internal fun Deserializer.deserializeArray(): JsonArray {
	advance() //skip [
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