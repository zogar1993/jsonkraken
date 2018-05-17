package jemzart.kjson.values

class JsonObject  internal constructor(override val value: LinkedHashMap<String, JsonValue>) : JsonValue {
	override fun iterator(): Iterator<JsonValue> = value.values.iterator()
	override fun get(key: String): JsonValue = value[key]!!
	override fun set(key: String, value: JsonValue) {
		this.value[key] = value
	}

	override fun toString(): String {
		val stringBuilder = StringBuilder()
		stringBuilder.append("{")
		var first = true
		for (pair in value) {
			if (!first) stringBuilder.append(",") else first = false
			stringBuilder.append("\"${pair.key}\":${pair.value}")
		}
		stringBuilder.append("}")
		return stringBuilder.toString()
	}

	override val jsonType get() = JsonType.Object
}