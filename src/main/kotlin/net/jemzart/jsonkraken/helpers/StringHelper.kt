package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException

//TODO Clean Up
internal fun throwIfIsNotAJsonCompliantString(string: String) {
	var i = 0
	val length = string.length
	fun assert(value: Boolean, message: () -> String) {
		if (!value) throw NonCompliantStringException(string, message())
	}
	while (true) {
		if (i == length) return
		if (string[i] == '\\') {
			i++ // skip \
			assert(i < length) { "Unescaped \\ at index ${i - 1}" }
			if (string[i] == 'u') {
				assert(i + 4 < length) { "Premature end of file encountered while parsing unicode" }
				repeat(4) { assert(string[++i].isHexadecimal()) { "Invalid hexadecimal character ${string[i]} at index $i" } }
			} else {
				assert(string[i] in Escapable.monoChars) { "Cant escape ${string[i]}" }
			}
		} else {
			val char = string[i]
			assert(char != '"') { "Unescaped \" at index $i" }
			assert(char !in Escapable.whiteSpaceChars) { "Unescaped white space character at index $i" }
			assert(!char.isISOControlCharacterOtherThanDelete()) { "Unescaped iso control character at index $i" }
			//TODO errores por assert, remover.
		}
		i++
	}
}