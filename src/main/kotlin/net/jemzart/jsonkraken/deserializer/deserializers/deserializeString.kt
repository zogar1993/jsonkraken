package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.deserializeString(): JsonString {
	val jsonNumber = JsonString()
	jsonNumber.value = deserializeRawString()
	return jsonNumber
}

internal fun Deserializer.deserializeRawString(): String {
	advance() //skip "
	val start = index

	while (!match('"'))
		deserializeCharacter()

	return raw.substring(start, index - 1)
}
