package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException

//TODO Clean Up
internal fun throwIfIsNotAJsonCompliantString(string: String) {
	var i = 0
	val length = string.length
	fun verify(value: Boolean, message: () -> String) {
		if (!value) throw NonCompliantStringException(string, message())
	}
	while (true) {
		if (i == length) return
		if (string[i] == '\\') {
			i++ // skip \
			verify(i < length) { "Unescaped \\ at index ${i - 1}" }
			if (string[i] == 'u') {
				verify(i + 4 < length) { "Premature end of string encountered while parsing unicode" }
				repeat(4) { verify(string[++i].isHexadecimal()) { "Invalid hexadecimal character ${string[i]} at index $i" } }
			} else {
				verify(string[i] in Escapable.monoChars) { "Cant escape ${string[i]}" }
			}
		} else {
			val char = string[i]
			verify(char != '"') { "Unescaped \" at index $i" }
			verify(char !in Escapable.whiteSpaceChars) { "Unescaped white space character at index $i" }
			verify(!char.isISOControlCharacterOtherThanDelete()) { "Unescaped iso control character at index $i" }
		}
		i++
	}
}