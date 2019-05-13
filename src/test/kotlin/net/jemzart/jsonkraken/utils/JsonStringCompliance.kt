package net.jemzart.jsonkraken.utils

import net.jemzart.jsonkraken.exceptions.NonCompliantStringException

class JsonStringCompliance {
	companion object {
		fun verify(action: (Any) -> Unit) {
			val errors = StringBuilder()
			for (invalid in invalids) {
				runCatching { action(invalid.second) }.onSuccess { errors.appendln("- \"${invalid.first}\" passed (should not have)") }.onFailure {
					if (it is NonCompliantStringException) assert(it.value == invalid.second.toString())
					else errors.appendln("- \"${invalid.first}\" threw unexpected exception)")
				}
			}
			if (errors.isNotEmpty()) assert(false) { errors }
		}

		private val invalids = arrayOf(
			"incomplete unicode" to "\\u000",
			"invalid unicode uZ000" to "\\uZ000",
			"invalid unicode u0Z00" to "\\u0Z00",
			"invalid unicode u00Z0" to "\\u00Z0",
			"invalid unicode u000Z" to "\\u000Z",
			"escaped non escapable character" to "\\z",
			"unescaped double quotes" to "\"",
			"unescaped double quotes character" to '\"',
			"unescaped reverse solidus" to "\\",
			"unescaped reverse solidus character" to '\\',
			"unescaped tab" to "\t",
			"unescaped tab character" to '\t',
			"unescaped carriage return" to "\r",
			"unescaped carriage return character" to '\r',
			"unescaped newline" to "\n",
			"unescaped newline character" to '\n')
	}
}