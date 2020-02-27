package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.throws.throwError
import net.jemzart.jsonkraken.helpers.isHexadecimal

internal fun Deserializer.validateIsHexadecimal(char: Char) {
	if (!char.isHexadecimal())
		throwError("Expected a hexadecimal character, found \"$char\".")
}