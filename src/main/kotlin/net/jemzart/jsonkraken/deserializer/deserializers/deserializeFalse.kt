package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.values.JsonFalse

internal fun Deserializer.deserializeFalse(): JsonFalse {
	advance() //skip f
	validateEquality(advance(), 'a')
	validateEquality(advance(), 'l')
	validateEquality(advance(), 's')
	validateEquality(advance(), 'e')
	return JsonFalse
}