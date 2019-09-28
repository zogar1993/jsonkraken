package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString

const val PARSING_OBJECT = "parsing object"
internal fun Deserializer.deserializeObject(): JsonObject {
	val obj = JsonObject()
	advance() //skip '{'
	skipWhiteSpaces()

	if (peek() != '}')
		while (true) {
			skipWhiteSpaces()
			deserializeObjectPair(obj)
			skipWhiteSpaces()

			if (peek() == ',') advance() //skip ,
			else if (peek() == '}') break
			else validateInclusion(peek(), arrayOf(',', '}'), PARSING_OBJECT)
		}

	advance() //skip '}'
	return obj
}

private fun Deserializer.deserializeObjectPair(obj: JsonObject) {
	val name = deserializeObjectKey()
	skipWhiteSpaces()
	validateEquality(advance(), ':', PARSING_OBJECT)
	skipWhiteSpaces()
	val value = deserializeValue()

	obj.map[name] = value
}

private fun Deserializer.deserializeObjectKey(): String {
	validateEquality(peek(), '\"', PARSING_OBJECT)
	return deserializeRawString()
}