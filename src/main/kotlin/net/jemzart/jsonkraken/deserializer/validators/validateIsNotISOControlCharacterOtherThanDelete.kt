package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete

internal fun Deserializer.validateIsNotISOControlCharacterOtherThanDelete(char: Char, context: String) {
	if (char.isISOControlCharacterOtherThanDelete())
		throwError(context, "\"$char\" is invalid in this context.")
}