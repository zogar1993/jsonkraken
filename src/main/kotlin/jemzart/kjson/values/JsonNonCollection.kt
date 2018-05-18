package jemzart.kjson.values

class JsonNonCollection(val value: Any?) : JsonValue(){
	val list = listOf(value)
	override fun get(key: String): JsonValue = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonValue) = throw UnsupportedOperationException()
	override fun <T> get(key: String, shamelessHack: T): T = throw UnsupportedOperationException()
	override fun set(key: String, value: Any?) = throw UnsupportedOperationException()
	override fun get(index: Int): JsonValue = throw UnsupportedOperationException()
	override fun set(index: Int, value: JsonValue) = throw UnsupportedOperationException()
	override fun <T> get(index: Int, shamelessHack: T): T = throw UnsupportedOperationException()
	override fun set(index: Int, value: Any?) = throw UnsupportedOperationException()
	override fun iterator(): Iterator<Any?> = list.iterator()
}