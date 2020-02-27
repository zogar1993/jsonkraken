package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwError

internal fun Deserializer.validateIsNotEndOfString() {
	if (isAtEnd()) throwError("Premature end of String.")
}