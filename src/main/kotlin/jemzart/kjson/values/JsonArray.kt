package jemzart.kjson.values

class JsonArray(override val value: MutableList<JsonValue>): JsonValue{
	override fun iterator(): Iterator<JsonValue> = value.iterator()
	override operator fun get(key: String) = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonValue) = throw UnsupportedOperationException()
	override val jsonType get() = JsonType.Array
	override fun toString(): String {
		val stringBuilder = StringBuilder()
		stringBuilder.append("[")
		var first = true
		for (item in value) {
			if (!first)stringBuilder.append(",") else first = false
			stringBuilder.append(item.toString())
		}
		stringBuilder.append("]")
		return stringBuilder.toString()
	}
}