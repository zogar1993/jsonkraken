package jemzart.kjson.values

class JsonArray internal constructor() : JsonValue {
	override fun get(index: Int): JsonValue {
		return list[index]
	}

	override fun set(index: Int, value: JsonValue) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	private val list: MutableList<JsonValue> = mutableListOf()
	override val value get() = throw NoSuchElementException()

	override fun iterator(): Iterator<JsonValue> = list.iterator()
	override operator fun get(key: String) = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonValue) = throw UnsupportedOperationException()
	override val jsonType get() = JsonType.Array
	override fun toString(): String {
		val stringBuilder = StringBuilder()
		stringBuilder.append("[")
		var first = true
		for (item in list) {
			if (!first) stringBuilder.append(",") else first = false
			stringBuilder.append(item.toString())
		}
		stringBuilder.append("]")
		return stringBuilder.toString()
	}

	fun add(item: JsonValue){
		list.add(item)
	}
}