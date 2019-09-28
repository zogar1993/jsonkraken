package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonArray

const val PARSING_ARRAY = "parsing array"
internal fun Deserializer.deserializeArray(): JsonArray {
	val arr = JsonArray()
	advanceAndTrim() //skip '['
	if (peek() != ']')
		while (true) {
			arr.uncheckedAdd(deserializeValue())

			if (peek() == ',') advanceAndTrim() //skip ','
			else if (peek() == ']') break
			else validateInclusion(peek(), arrayOf(',', ']'), PARSING_ARRAY)
		}
	advanceAndTrim() //skip ']'
	return arr
}