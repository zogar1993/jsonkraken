package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.values.JsonFalse

internal fun Deserializer.deserializeFalse(): JsonFalse {
	consume('a')
	consume('l')
	consume('s')
	consume('e')
	return JsonFalse
}