package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.*

internal class Serializer constructor(private val value: JsonValue, formatted: Boolean) {
	private val stb = StringBuilder()
	private operator fun StringBuilder.plusAssign(value: String) {
		this.append(value)
	}

	private val indentation = "\t"
	private var nesting = 0
	private inline val tabs get() = indentation.repeat(nesting)

	private val writeKey: (String)->Unit
	private val writeStart: (String)->Unit
	private val writeEnd: (String)->Unit
	private val writeDelimiter: ()->Unit
	private val writeTabs: ()->Unit


	init {
		if (formatted) {
			writeKey = { stb += "\"$it\": " }
			writeStart = { stb += "$it\n"; ++nesting }
			writeEnd = { stb += "\n"; --nesting; stb += "$tabs$it" }
			writeDelimiter = { stb += ",\n$tabs" }
			writeTabs = { stb += tabs }
		} else {
			writeKey = {  stb += "\"$it\":" }
			writeStart = { stb += it }
			writeEnd = { stb += it }
			writeDelimiter = { stb += "," }
			writeTabs = { stb += "" }
		}
	}

	fun create(): String {
		writeValue(value)
		return stb.toString()
	}

	private fun writeValue(value: JsonValue) {
		when (value) {
			is JsonArray -> writeArray(value)
			is JsonObject -> writeObject(value)
			else -> parsePrimitive(value)
		}
	}

	private fun writeObject(obj: JsonObject) {
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

	private fun writeArray(arr: JsonArray) {
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

	private fun parsePrimitive(value: JsonValue) {
		val str = when (value) {
			is JsonNull -> "null"
			is JsonString -> "\"${value.value}\""
			is JsonBoolean -> value.cast<Boolean>().toString()
			is JsonNumber -> {
				val double = value.cast<Double>()//TODO esto esta mal
				if (double % 1.0 == 0.0) "${double.toLong()}" else "$double"
			}
			else -> throw InvalidJsonTypeException(value)
		}
		stb.append(str)
	}
}