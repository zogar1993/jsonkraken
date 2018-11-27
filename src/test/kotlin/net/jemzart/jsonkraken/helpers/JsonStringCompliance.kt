package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.NonCompliantStringException


class JsonStringCompliance {
	companion object {
		fun verify(action: (Any) -> Unit) {
			val errors = StringBuilder()
			for (valid in valids) {
				valid.second.runCatching { action(this) }.
					onFailure { errors.appendln("- \"${valid.first}\" did not pass (should have)") }
			}
			for (invalid in invalids) {
				invalid.second.runCatching { action(this) }.
					onSuccess { errors.appendln("- \"${invalid.first}\" passed (should not have)") }.
					onFailure {
						if (it is NonCompliantStringException) assert(it.value == invalid.second.toString())
						else errors.appendln("- \"${invalid.first}\" threw unexpected exception)")
					}
			}
			if (errors.isNotEmpty()) assert(false) { errors }
		}

		private val valids = arrayOf(
			"empty" to "",
			"word" to "kraken",
			"escaped r" to "\\r",
			"escaped n" to "\\n",
			"escaped t" to "\\t",
			"escaped b" to "\\b",
			"escaped f" to "\\f",
			"escaped solidus" to "\\/",
			"escaped double quotes" to "\\\"",
			"escaped reverse solidus" to "\\\\",
			"UTF-16" to "\\uAF09")

		private val invalids = arrayOf(
			"incomplete UTF-16" to "\\u000",
			"invalid UTF-16 Z000" to "\\uZ000",
			"invalid UTF-16 0Z00" to "\\u0Z00",
			"invalid UTF-16 00Z0" to "\\u00Z0",
			"invalid UTF-16 000Z" to "\\u000Z",
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