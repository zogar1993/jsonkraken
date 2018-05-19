package jemzart.kjson.parsers

import jemzart.kjson.values.JsonArray
import jemzart.kjson.values.JsonNonCollection
import jemzart.kjson.values.JsonObject
import jemzart.kjson.values.JsonValue

class ObjectToStringParser internal constructor(jsonValue: JsonValue){
	private val stb = StringBuilder()
	init {
		when(jsonValue){
			is JsonNonCollection -> parsePrimitive(jsonValue.value)
			is JsonArray -> parseArray(jsonValue)
			is JsonObject -> parseObject(jsonValue)
			else -> throw UnsupportedOperationException()
		}
	}

	fun create() = stb.toString()

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