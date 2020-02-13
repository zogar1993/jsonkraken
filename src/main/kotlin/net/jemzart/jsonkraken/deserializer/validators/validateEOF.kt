package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.errors.throwError

internal fun Deserializer.validateEOF() {
	if (!isAtEnd())
		throwError(
			"Invalid characters have been found after the end of the outermost json structure. " +
				"Should they be removed, the parse would succeed.")
}

//todo add suggestions