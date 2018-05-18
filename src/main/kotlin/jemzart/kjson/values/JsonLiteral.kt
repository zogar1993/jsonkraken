package jemzart.kjson.values

abstract class JsonLiteral internal constructor() : JsonValue {
	override fun iterator(): Iterator<JsonValue> = throw UnsupportedOperationException()
	override operator fun get(key: String) = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonValue) = throw UnsupportedOperationException()
	override operator fun get(index: Int) = throw UnsupportedOperationException()
	override fun set(index: Int, value: JsonValue) = throw UnsupportedOperationException()
	override val jsonType get() = JsonType.Literal
}