package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonValue


internal class TemplateSerializer constructor(private val value: JsonValue, tabulation: Tabulation) : Serializer() {
	private operator fun StringBuilder.plusAssign(value: String) {
		this.append(value)
	}

	private val indentation = tabulation.value
	private var nesting = 0
	private val tabs get() = indentation.repeat(nesting)

	private fun writeKey(it: String) {
		stb += "\"$it\": "
	}

	private fun writeStart(it: String) {
		stb += "$it\n"; ++nesting
	}

	private fun writeEnd(it: String) {
		stb += "\n"; --nesting; stb += "$tabs$it"
	}

	private fun writeDelimiter() {
		stb += ",\n$tabs"
	}

	private fun writeTabs() {
		stb += tabs
	}

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
			writeKey(pair.key)
			writeValue(pair.value)
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