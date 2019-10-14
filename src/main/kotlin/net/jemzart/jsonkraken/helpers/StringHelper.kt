package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException

internal fun throwIfIsNotAJsonCompliantString(string: String) {
	var i = 0
	val length = string.length
	while (i < length) {
		if (string[i] == '\\') {
			i++ // skip \
			if (i >= length) throwUnescapedBackslashFound(i, string)
			if (string[i] == 'u') {
				if (i + 4 >= length) throwInvalidUnicodeFound(i, string)
				if (string[++i].isNotHexadecimal()) throwInvalidUnicodeFound(i, string)
				if (string[++i].isNotHexadecimal()) throwInvalidUnicodeFound(i, string)
				if (string[++i].isNotHexadecimal()) throwInvalidUnicodeFound(i, string)
				if (string[++i].isNotHexadecimal()) throwInvalidUnicodeFound(i, string)
			} else {
				if (string[i] !in Escapable.monoChars) throwUnescapedBackslashFound(i, string)
			}
		} else {
			val char = string[i]
			if (char == '"') throwUnescapedDoubleQuotesFound(i, string)
			if (char in Escapable.whiteSpaceChars) throwUnescapedWhiteSpaceCharacterFound(i, string)
			if (char.isISOControlCharacterOtherThanDelete()) throwUnescapedIsoControlCharacterFound(i, string)
		}
		i++
	}
}

private fun throwUnescapedBackslashFound(i: Int, string: String): Nothing {
	throwNonCompliantString("Unescaped \\ at index ${i - 1}", string)
}

private fun throwInvalidUnicodeFound(i: Int, string: String): Nothing {
	throwNonCompliantString("Invalid hexadecimal character ${string[i]} at index $i", string)
}

private fun throwUnescapedDoubleQuotesFound(i: Int, string: String): Nothing {
	throwNonCompliantString("Unescaped \" at index $i", string)
}

private fun throwUnescapedWhiteSpaceCharacterFound(i: Int, string: String): Nothing {
	throwNonCompliantString("Unescaped white space character at index $i", string)
}

private fun throwUnescapedIsoControlCharacterFound(i: Int, string: String): Nothing {
	throwNonCompliantString("Unescaped iso control character at index $i", string)
}

private fun throwNonCompliantString(message: String, string: String): Nothing {
	throw NonCompliantStringException(string, message)
}