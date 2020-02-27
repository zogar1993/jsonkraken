package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateExclusion
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.deserializer.validators.validateIsHexadecimal
import net.jemzart.jsonkraken.deserializer.validators.validateIsNotISOControlCharacterOtherThanDelete

internal fun Deserializer.deserializeCharacter() {
	if (match('\\'))
		deserializeEscapableSequence()
	else
		deserializeNormalCharacter()
}

private fun Deserializer.deserializeNormalCharacter() {
	validateNormalCharacter(next())
}

private fun Deserializer.validateNormalCharacter(char: Char) {
	validateExclusion(char, Escapable.whiteSpaceChars)
	validateIsNotISOControlCharacterOtherThanDelete(char)
}

private fun Deserializer.deserializeEscapableSequence() {
	if (match('u'))
		deserializeStringUnicode()
	else
		deserializeEscapableChar()
}

private fun Deserializer.deserializeEscapableChar() {
	validateInclusion(next(), Escapable.monoChars)
}

private fun Deserializer.deserializeStringUnicode() {
	validateIsHexadecimal(next())
	validateIsHexadecimal(next())
	validateIsHexadecimal(next())
	validateIsHexadecimal(next())
}
