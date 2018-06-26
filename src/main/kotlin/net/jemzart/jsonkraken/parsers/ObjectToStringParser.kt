package net.jemzart.jsonkraken.parsers

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ObjectToStringParser internal constructor(private val value: Any?){
	private val stb = StringBuilder()

	fun create(): String {
		parseValue(value)
		return stb.toString()
	}

	private fun parseValue(value: Any?){
		when(value) {
			is JsonArray -> parseArray(value)
			is JsonObject -> parseObject(value)
			else -> parsePrimitive(value)
		}
	}

	private fun parseObject(obj: JsonObject) {
		stb.append("{")
		var first = true
		for (pair in obj) {
			if (!first) stb.append(",") else first = false
			stb.append("\"${pair.first}\":")
			parseValue(pair.second)
		}
		stb.append("}")
	}
	private fun parseArray(arr: JsonArray) {
		stb.append("[")
		var first = true
		for (item in arr) {
			if (!first) stb.append(",") else first = false
			parseValue(item)
		}
		stb.append("]")
	}

	private fun parsePrimitive(value: Any?){
		val str = when(value) {
			null -> "null"
			is String -> "\"$value\""
			else -> value.toString()
		}
		stb.append(str)
	}
}