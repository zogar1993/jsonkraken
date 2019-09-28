package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateExclusion
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.deserializer.validators.validateIsHexadecimal
import net.jemzart.jsonkraken.deserializer.validators.validateIsNotISOControlCharacterOtherThanDelete
import net.jemzart.jsonkraken.values.JsonString

const val PARSING_STRING = "parsing string"
internal fun Deserializer.deserializeString(): JsonString {
	val json = JsonString()
	json.value = deserializeRawString()
	return json
}

internal fun Deserializer.deserializeRawString(): String {
	advance() //skip "
	val start = index

	while (peek() != '"')
		deserializeCharacter()

	val value = raw.substring(start, index)
	advance() //skip "
	return value
}

private fun Deserializer.deserializeCharacter() {
	if (peek() == '\\')
		deserializeEscapableSequence()
	else
		deserializeNormalCharacter()
}

private fun Deserializer.deserializeNormalCharacter() {
	validateExclusion(peek(), Escapable.whiteSpaceChars, PARSING_STRING)
	validateIsNotISOControlCharacterOtherThanDelete(peek(), PARSING_STRING)
	advance() //skip 1 char
}

private fun Deserializer.deserializeEscapableSequence() {
	advance() // skip \

	if (peek() == 'u')
		deserializeStringUnicode()
	else
		deserializeEscapableChar()
}

private fun Deserializer.deserializeEscapableChar() {
	validateInclusion(advance(), Escapable.monoChars, PARSING_STRING)
}

private fun Deserializer.deserializeStringUnicode() {
	advance() //skip u
	validateIsHexadecimal(advance(), PARSING_STRING)
	validateIsHexadecimal(advance(), PARSING_STRING)
	validateIsHexadecimal(advance(), PARSING_STRING)
	validateIsHexadecimal(advance(), PARSING_STRING)
}


