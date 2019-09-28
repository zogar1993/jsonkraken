package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonObject

const val PARSING_OBJECT = "parsing object"
internal fun Deserializer.deserializeObject(): JsonObject {
	val obj = JsonObject()
	advanceAndTrim() //skip '{'

	if (peek() != '}')
		while (true) {
			validateEquality(peek(), '\"', PARSING_OBJECT)

			val name = deserializeString()

			validateEquality(peek(), ':', PARSING_OBJECT)
			advanceAndTrim() //skip :

			obj.uncheckedSet(name, deserializeValue())

			if (peek() == ',') advanceAndTrim() //skip ,
			else if (peek() == '}') break
			else validateInclusion(peek(), arrayOf(',', '}'), PARSING_OBJECT)
		}
	advanceAndTrim() //skip '}'
	return obj
}