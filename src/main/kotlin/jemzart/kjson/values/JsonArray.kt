package jemzart.kjson.values

class JsonArray internal constructor() : JsonCollection {
	private val list: MutableList<Any?> = mutableListOf()

	override operator fun get(key: String) = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonCollection) = throw UnsupportedOperationException()
	override fun <T> get(key: String, shamelessHack: T): T = throw UnsupportedOperationException()
	override fun set(key: String, value: Any?) = throw UnsupportedOperationException()

	override fun iterator(): Iterator<Any?> = list.iterator()

	override fun <T> get(index: Int, shamelessHack: T): T = list[index] as T
	override fun get(index: Int): JsonCollection = get(index, JsonCollection.dummy)

	override fun set(index: Int, value: Any?) {
		list[index] = value
	}
	override fun set(index: Int, value: JsonCollection) = set(index, value as Any)

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

	fun add(item: Any?){
		list.add(item)
	}
}