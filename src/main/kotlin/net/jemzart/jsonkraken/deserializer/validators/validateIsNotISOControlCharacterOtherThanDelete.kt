package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwError
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete

internal fun Deserializer.validateIsNotISOControlCharacterOtherThanDelete(char: Char) {
	if (char.isISOControlCharacterOtherThanDelete())
		throwError("\"$char\" is invalid in this context.")
}