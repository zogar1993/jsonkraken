package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateEquality
import net.jemzart.jsonkraken.values.JsonTrue

internal fun Deserializer.deserializeTrue(): JsonTrue {
	advance() //skip t
	consume('r')
	consume('u')
	consume('e')
	return JsonTrue
}