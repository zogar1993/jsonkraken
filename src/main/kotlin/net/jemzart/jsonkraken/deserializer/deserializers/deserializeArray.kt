package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.values.JsonArray

const val PARSING_ARRAY = "parsing array"
internal fun Deserializer.deserializeArray(): JsonArray {
	val arr = JsonArray()
	advance() //skip '['
	skipWhiteSpaces()

	if (peek() != ']')
		while (true) {
			skipWhiteSpaces()
			deserializeArrayItem(arr)
			skipWhiteSpaces()

			if (peek() == ',') advance() //skip ','
			else if (peek() == ']') break
			else validateInclusion(peek(), arrayOf(',', ']'))
		}

	advance() //skip ']'
	return arr
}

private fun Deserializer.deserializeArrayItem(arr: JsonArray) {
	val item = deserializeValue()
	arr.list.add(item)
}