package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonNull

private const val PARSING_NULL = "parsing null"
internal fun Deserializer.deserializeNull(): JsonNull {
	validateEquality(advance(), 'n', PARSING_NULL)
	validateEquality(advance(), 'u', PARSING_NULL)
	validateEquality(advance(), 'l', PARSING_NULL)
	validateEquality(advance(), 'l', PARSING_NULL)
	return JsonNull
}