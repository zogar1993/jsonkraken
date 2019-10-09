package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.validateIsDecimal(char: Char) {
	if (!char.isDecimal())
		throwError("Expected a decimal character, found \"$char\".")
}

private fun Char.isDecimal(): Boolean {
	val codePoint = this.toByte()
	return codePoint in 48..57 // 0-9
}