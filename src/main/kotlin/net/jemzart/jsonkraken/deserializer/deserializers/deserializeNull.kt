package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.JsonNull


internal fun Deserializer.deserializeNull(): JsonNull {
	advance() //skip n
	consume('u')
	consume('l')
	consume('l')
	return JsonNull
}