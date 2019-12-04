package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.values.*
import net.jemzart.jsonkraken.values.JsonArray






internal class FormattedSerializer constructor(private val value: JsonValue): Serializer() {
	private operator fun StringBuilder.plusAssign(value: String) {
		this.append(value)
	}

	private val indentation = "\t"
	private var nesting = 0
	private inline val tabs get() = indentation.repeat(nesting)

	private val writeKey: (String)->Unit = { stb += "\"$it\": " }
	private val writeStart: (String)->Unit = { stb += "$it\n"; ++nesting }
	private val writeEnd: (String)->Unit = { stb += "\n"; --nesting; stb += "$tabs$it" }
	private val writeDelimiter: ()->Unit = { stb += ",\n$tabs" }
	private val writeTabs: ()->Unit = { stb += tabs }

	fun create(): String {
		writeValue(value)
		return stb.toString()
	}

	override fun writeObject(obj: JsonObject) {
		writeStart("{")
		var first = true
		for (pair in obj) {
			if (first) {
				writeTabs(); first = false
			} else writeDelimiter()
			writeKey(pair.first)
			writeValue(pair.second)
		}
		writeEnd("}")
	}

	override fun writeArray(arr: JsonArray) {
		writeStart("[")
		var first = true
		for (item in arr) {
			if (first) {
				writeTabs(); first = false
			} else writeDelimiter()
			writeValue(item)
		}
		writeEnd("]")
	}
}