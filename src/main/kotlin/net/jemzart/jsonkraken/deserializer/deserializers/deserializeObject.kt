package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwExpectationFailed
import net.jemzart.jsonkraken.deserializer.validators.validateEquality


internal fun Deserializer.deserializeObject(): JsonObject {
	advance() //skip {
	skipWhiteSpaces()

	if (match('}')) return JsonObject()

	val obj = JsonObject()
	while (true) {
		skipWhiteSpaces()
		deserializeObjectPair(obj)
		skipWhiteSpaces()

		if (match(',')) continue
		if (match('}')) return obj
		throwExpectationFailed(current(), arrayOf(',', '}'))
	}
}

private fun Deserializer.deserializeObjectPair(obj: JsonObject) {
	val key = deserializeObjectKey()
	skipWhiteSpaces()
	consume(':')
	skipWhiteSpaces()
	val value = deserializeValue()

	obj.hashMap[key] = value
}

private fun Deserializer.deserializeObjectKey(): String {
	validateEquality(peek(), '\"')
	return deserializeRawString()
}