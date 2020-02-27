package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.helpers.simplifyJsonNumber

internal fun Deserializer.deserializeNumberStartingWithZero(): JsonNumber {
	val start = index
	zero()
	return deserializeNumber(start)
}

internal fun Deserializer.deserializeNumberLowerThanZero(): JsonNumber {
	val start = index
	minus()
	return deserializeNumber(start)
}

internal fun Deserializer.deserializeNumberEqualOrHigherThanOne(): JsonNumber {
	val start = index
	oneToNine()
	return deserializeNumber(start)
}

private fun Deserializer.deserializeNumber(start: Int): JsonNumber {
	val jsonNumber = JsonNumber()
	jsonNumber.value = simplifyJsonNumber(raw.substring(start, index))
	return jsonNumber
}
