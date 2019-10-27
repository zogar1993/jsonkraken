package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.*

internal class SimpleSerializer constructor(private val value: JsonValue) {
	private val stb = StringBuilder()

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
		stb.append("{")
		var first = true
		for (pair in obj) {
			if (first) first = false
			else stb.append(",")
			stb.append("\"")
			stb.append(pair.first)
			stb.append("\":")
			writeValue(pair.second)
		}
		stb.append("}")
	}

	private fun writeArray(arr: JsonArray) {
		stb.append( "[")
		var first = true
		for (item in arr) {
			if (first) first = false
			else stb.append(",")
			writeValue(item)
		}
		stb.append("]")
	}

	private fun parsePrimitive(value: JsonValue) {
		when (value) {
			is JsonNull -> stb.append("null")
			is JsonBoolean -> stb.append("${value.value}")
			is JsonNumber -> {
				val double = value.cast<Double>()//TODO esto esta mal
				stb.append(if (double % 1.0 == 0.0)
					"${double.toLong()}"
				else
					"$double")
			}
			is JsonString -> {
				stb.append("\"")
				stb.append(value.value)
				stb.append("\"")
			}
			else -> throw InvalidJsonTypeException(value)
		}
	}
}