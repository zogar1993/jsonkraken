package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer

internal fun Deserializer.validateIsDecimal(char: Char, context: String) {
		if (!char.isDecimal())
			throwError(context, "Expected a decimal character, found \"$char\".")
	}

private fun Char.isDecimal(): Boolean {
	val codePoint = this.toByte()
	return codePoint in 48..57 // 0-9
}