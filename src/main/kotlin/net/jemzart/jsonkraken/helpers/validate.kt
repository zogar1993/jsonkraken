package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.InvalidJsonStringException

internal fun String.validate() {
		var i = 0
		fun assert (value: Boolean, message: () -> String) {
			if (!value) {
				throw InvalidJsonStringException(this, "String is not valid for JSON specification.\n${message()}")
			}
		}
		while (true) {
			if (i == length) return
			if (this[i] == '\\') {
				i++ // skip \
				assert(i < length) { "Unescaped \\ at index ${i - 1}" }
				if (this[i] == 'u') {
					assert(i + 4 < length) { "Premature end of file encountered while parsing \\uFFFF" }
					assert(this[++i].isHexadecimal()) { "Invalid hexadecimal character ${this[i]} at index $i" }
					assert(this[++i].isHexadecimal()) { "Invalid hexadecimal character ${this[i]} at index $i" }
					assert(this[++i].isHexadecimal()) { "Invalid hexadecimal character ${this[i]} at index $i" }
					assert(this[++i].isHexadecimal()) { "Invalid hexadecimal character ${this[i]} at index $i" }
				} else {
					assert(this[i] in Escapable.monoChars) { "Cant escape ${this[i]}" }
				}
			} else {
				val char = this[i]
				assert(char != '"') { "Unescaped \" at index $i" }
				assert(char !in Escapable.whiteSpaceChars) { "Unescaped white space character at index $i" }
				assert(!char.isISOControlCharacterOtherThanDelete()) { "Unescaped iso control character at index $i" }
			}
			i++
		}
	}