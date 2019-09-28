package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.helpers.isHexadecimal
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonString

const val PARSING_STRING = "parsing string"
internal fun Deserializer.deserializeString(): JsonString {
	advance() //skip "
	val valueStart = index
	while (true) {
		if (peek() == '\\') {
			advance() // skip \

			if (peek() == 'u') {
				advance()
				validateIsHexadecimal(advance(), PARSING_STRING)
				validateIsHexadecimal(advance(), PARSING_STRING)
				validateIsHexadecimal(advance(), PARSING_STRING)
				validateIsHexadecimal(advance(), PARSING_STRING)
//					advance() //skip uFFFF
			} else {
				validateInclusion(peek(), Escapable.monoChars, PARSING_STRING)
				advance() //skip 1 char
			}
		} else if (peek() == '"') {
			val value = raw.substring(valueStart, index)
			advanceAndTrim() //skip "
			return JsonString(value)//TODO duplicada validación
		} else {
			validateExclusion(peek(), Escapable.whiteSpaceChars, PARSING_STRING)
			validateIsNotISOControlCharacterOtherThanDelete(peek(), PARSING_STRING)
			advance() //skip 1 char
		}
	}
}

private fun Deserializer.validateIsNotISOControlCharacterOtherThanDelete(char: Char, context: String) {
	if (char.isISOControlCharacterOtherThanDelete())
		throwError(context, "\"$char\" is invalid in this context.")
}

private fun Deserializer.validateExclusion(char: Char, expectations: Array<Char>, context: String) {
	for (expectation in expectations)
		if (char == expectation) {
			val arr = JsonArray(expectations).toString()
			throwError(context, "None of $arr expected, found \"$char\".")
		}
}


private fun Deserializer.validateIsHexadecimal(char: Char, context: String) {
	if (!char.isHexadecimal())
		throwError(context, "Expected a hexadecimal character, found \"$char\".")
}