package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.helpers.isHexadecimal

internal fun Deserializer.validateIsHexadecimal(char: Char, context: String) {
	if (!char.isHexadecimal())
		throwError(context, "Expected a hexadecimal character, found \"$char\".")
}