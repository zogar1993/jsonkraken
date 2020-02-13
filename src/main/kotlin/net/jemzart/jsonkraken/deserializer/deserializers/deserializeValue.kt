package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.errors.throwError
import net.jemzart.jsonkraken.JsonValue

internal fun Deserializer.deserializeValue(): JsonValue {
	return when (val char = peek()) {
		'{' -> deserializeObject()
		'[' -> deserializeArray()
		'\"' -> deserializeString()
		't' -> deserializeTrue()
		'f' -> deserializeFalse()
		'n' -> deserializeNull()
		'-' -> deserializeNumberLowerThanZero()
		'0' -> deserializeNumberStartingWithZero()
		in '1'..'9' -> deserializeNumberEqualOrHigherThanOne()
		else -> throwError("No JSON token starts with '$char'.")
	}
}