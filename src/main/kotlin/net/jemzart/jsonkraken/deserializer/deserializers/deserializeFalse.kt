package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonFalse
import net.jemzart.jsonkraken.deserializer.Deserializer


internal fun Deserializer.deserializeFalse(): JsonFalse {
	advance() //skip f
	consume('a')
	consume('l')
	consume('s')
	consume('e')
	return JsonFalse
}