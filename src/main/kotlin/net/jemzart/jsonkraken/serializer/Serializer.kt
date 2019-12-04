package net.jemzart.jsonkraken.serializer

import net.jemzart.jsonkraken.*

abstract class Serializer {
	protected val stb = StringBuilder()

	protected fun writeValue(value: JsonValue) {
		when (value) {
			is JsonArray -> writeArray(value)
			is JsonObject -> writeObject(value)
			is JsonPrimitive<*> -> parsePrimitive(value)
		}
	}

	abstract fun writeArray(arr: JsonArray)

	abstract fun writeObject(obj: JsonObject)

	private fun parsePrimitive(primitive: JsonPrimitive<*>) {
		when (primitive) {
			is JsonNull -> stb.append("null")
			is JsonBoolean -> stb.append("${primitive.value}")
			is JsonNumber -> stb.append("${primitive.value}")
			is JsonString -> stb.append("\"").append(primitive.value).append("\"")
		}
	}
}