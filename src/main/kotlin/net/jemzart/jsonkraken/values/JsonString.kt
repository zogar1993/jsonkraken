package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.constants.Escapable
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException
import net.jemzart.jsonkraken.helpers.isHexadecimal
import net.jemzart.jsonkraken.helpers.isISOControlCharacterOtherThanDelete

class JsonString(string: String) : JsonValue(), JsonCasteable by Companion {
	val value = string.validate()
	private fun String.validate(): String {
		var i = 0
		fun assert(value: Boolean, message: () -> String) {
			if (!value) throw NonCompliantStringException(this, message())
		}
		while (true) {
			if (i == length) return this
			if (this[i] == '\\') {
				i++ // skip \
				assert(i < length) { "Unescaped \\ at index ${i - 1}" }
				if (this[i] == 'u') {
					assert(i + 4 < length) { "Premature end of file encountered while parsing \\uFFFF" }
					repeat(4) { assert(this[++i].isHexadecimal()) { "Invalid hexadecimal character ${this[i]} at index $i" } }
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

	override fun equals(other: Any?): Boolean {
		if (other !is JsonString) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}

	private companion object: JsonCasteable {
		override val casts get() =
			JsonValue.casts + (JsonString::class to { value: Any -> value }) +
					(CharSequence::class to { value -> (value as JsonString).value }) +
					(String::class to { value -> (value as JsonString).value })
	}
}