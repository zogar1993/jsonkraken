package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonFalse

const val PARSING_FALSE = "parsing false"
internal fun Deserializer.deserializeFalse(): JsonFalse {
	validateEquality(advance(), 'f', PARSING_FALSE)
	validateEquality(advance(), 'a', PARSING_FALSE)
	validateEquality(advance(), 'l', PARSING_FALSE)
	validateEquality(advance(), 's', PARSING_FALSE)
	validateEquality(advance(), 'e', PARSING_FALSE)
	return JsonFalse
}