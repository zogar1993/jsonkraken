package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonTrue
import net.jemzart.jsonkraken.deserializer.Deserializer


internal fun Deserializer.deserializeTrue(): JsonTrue {
	advance() //skip t
	consume('r')
	consume('u')
	consume('e')
	return JsonTrue
}