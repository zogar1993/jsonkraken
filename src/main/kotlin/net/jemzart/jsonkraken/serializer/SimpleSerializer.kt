package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.values.*
import net.jemzart.jsonkraken.values.JsonArray

internal class SimpleSerializer constructor(private val value: JsonValue): Serializer() {
	fun create(): String {
		writeValue(value)
		return stb.toString()
	}

	override fun writeObject(obj: JsonObject) {
		stb.append("{")
		var first = true
		for (pair in obj) {
			if (first) first = false
			else stb.append(",")
			stb.append("\"").append(pair.first).append("\":")
			writeValue(pair.second)
		}
		stb.append("}")
	}

	override fun writeArray(arr: JsonArray) {
		stb.append( "[")
		var first = true
		for (item in arr) {
			if (first) first = false
			else stb.append(",")
			writeValue(item)
		}
		stb.append("]")
	}
}