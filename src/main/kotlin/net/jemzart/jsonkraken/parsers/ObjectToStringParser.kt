package net.jemzart.jsonkraken.parsers

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ObjectToStringParser internal constructor(private val value: Any?,
                                                formatted: Boolean) {
	private val stb = StringBuilder()
	private val indentation = "\t"
	private var nesting = 0
	private inline val tabs get() = indentation.repeat(nesting)

	val writeKey =
		if (formatted) fun (key:String) { stb.append("\"$key\": ") }
		else fun (key:String) { stb.append("\"$key\":") }

	val writeStart =
		if (formatted) fun(value:String) { stb.append("$value\n"); ++nesting; stb.append(tabs) }
		else fun(value:String) { stb.append(value) }


	val writeEnd =
		if (formatted) fun(value:String) { stb.append("\n"); --nesting; stb.append("$tabs$value")  }
		else fun(value:String) { stb.append(value) }

	val writeDelimiter =
		if (formatted) fun() { stb.append(",\n$tabs")  }
		else fun() { stb.append(",") }

	fun create(): String {
		writeValue(value)
		return stb.toString()
	}

	private fun writeValue(value: Any?) {
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
			if (!first) writeDelimiter() else first = false
			writeKey(pair.first)
			writeValue(pair.second)
		}
		writeEnd("}")
	}

	private fun writeArray(arr: JsonArray) {
		writeStart("[")
		var first = true
		for (item in arr) {
			if (!first) writeDelimiter() else first = false
			writeValue(item)
		}
		writeEnd("]")
	}

	private fun parsePrimitive(value: Any?) {
		val str = when (value) {
			null -> "null"
			is String, is Char-> "\"$value\""
			is Boolean -> value.toString()
			is Number -> (if (value.toDouble() % 1.0 == 0.0) value.toLong() else value).toString()
			else -> throw InvalidJsonTypeException(value)
		}
		stb.append(str)
	}
}