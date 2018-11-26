package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.InvalidJsonStringException

internal fun String.validate() {
		var i = 0
		fun assert (value: Boolean, message: () -> String) {
			if (!value) throw InvalidJsonStringException(this, message())
		}
		while (true) {
			if (i == length) return
			if (this[i] == '\\') {
				i++ // skip \
				assert(i < length) { "unescaped \\" }
				if (this[i] == 'u') {
					assert(i + 4 < length) { "premature end of file encountered" }
					assert(this[++i].isHexadecimal()) { "invalid hexadecimal character" }
					assert(this[++i].isHexadecimal()) { "invalid hexadecimal character" }
					assert(this[++i].isHexadecimal()) { "invalid hexadecimal character" }
					assert(this[++i].isHexadecimal()) { "invalid hexadecimal character" }
				} else {
					assert(this[i] in Escapable.monoChars) { "cant escape ${this[i]}" }
				}
			} else {
				val char = this[i]
				assert(char != '"') { "unescaped \"" }
				assert(char !in Escapable.whiteSpaceChars) { "unescaped white space character" }
				assert(!char.isISOControlCharacterOtherThanDelete()) { "unescaped iso control character" }
			}
			i++
		}
	}