package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException

internal fun throwIfIsNotAJsonCompliantString(string: String) {
	var i = 0
	while (i < string.length) {
		if (string[i] == '\\') {
			i++ // skip \
			throwIfIsUnescapedBackslash(string, i)
			if (string[i] == 'u') {
				throwIfUnicodeIsTooShort(string, i)
				throwIfNonHexadecimalValueInsideUnicode(string, ++i)
				throwIfNonHexadecimalValueInsideUnicode(string, ++i)
				throwIfNonHexadecimalValueInsideUnicode(string, ++i)
				throwIfNonHexadecimalValueInsideUnicode(string, ++i)
			} else
				throwIfEscapingNonEscapableCharacter(string, i)
		} else {
			throwIfUnescapedDoubleQuotes(string, i)
			throwIfUnescapedWhiteSpaceCharacter(string, i)
			throwIfISOControlCharacterOtherThanDelete(string, i)
		}
		i++
	}
}

private fun throwIfISOControlCharacterOtherThanDelete(string: String, i: Int) {
	if (string[i].isISOControlCharacterOtherThanDelete())
		throwNonCompliantString("Unescaped iso control character at index $i", string)
}

private fun throwIfUnescapedWhiteSpaceCharacter(string: String, i: Int) {
	if (string[i] in Escapable.whiteSpaceChars)
		throwNonCompliantString("Unescaped white space character at index $i", string)
}

private fun throwIfUnescapedDoubleQuotes(string: String, i: Int) {
	if (string[i] == '"')
		throwNonCompliantString("Unescaped \" at index $i", string)
}

private fun throwIfEscapingNonEscapableCharacter(string: String, i: Int) {
	if (string[i] !in Escapable.monoChars)
		throwNonCompliantString("Unescapable character '${string[i]}' found at index $i", string)
}

private fun throwIfNonHexadecimalValueInsideUnicode(string: String, i: Int) {
	if (string[i].isNotHexadecimal())
		throwNonCompliantString("Invalid hexadecimal character '${string[i]}' at index $i", string)
}

private fun throwIfIsUnescapedBackslash(string: String, i: Int) {
	if (i >= string.length)
		throwNonCompliantString("Unescaped \\ at end of string", string)
}

private fun throwIfUnicodeIsTooShort(string: String, i: Int) {
	if (i + 4 >= string.length)
		throwNonCompliantString("Expected four hexadecimal characters but found end of string", string)
}
//TODO TEST

private fun throwNonCompliantString(message: String, string: String): Nothing =
	throw NonCompliantStringException(string, message)
