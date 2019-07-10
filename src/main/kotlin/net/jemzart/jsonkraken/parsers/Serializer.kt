package net.jemzart.jsonkraken.parsers

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

	private val writeKey =
		if (formatted) { key: String -> stb += "\"$key\": " }
		else { key: String -> stb += "\"$key\":" }

	private val writeStart =
		if (formatted) { value: String -> stb += "$value\n"; ++nesting; Unit }
		else { value: String -> stb += value }


	private val writeEnd =
		if (formatted) { value: String -> stb += "\n"; --nesting; stb += "$tabs$value" }
		else { value: String -> stb += value }

	private val writeDelimiter =
		if (formatted) {
			{ stb += ",\n$tabs" }
		} else {
			{ stb += "," }
		}

	private val writeTabs =
		if (formatted) {
			{ stb += tabs }
		} else {
			{ stb += "" }
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
				val double = value.cast<Double>()
				if (double % 1.0 == 0.0) double.toLong().toString() else double.toString()
			}
			else -> throw InvalidJsonTypeException(value)
		}
		stb.append(str)
	}
}