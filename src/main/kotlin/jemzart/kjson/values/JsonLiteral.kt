package jemzart.kjson.values

abstract class JsonLiteral: JsonValue{
	override fun iterator(): Iterator<JsonValue> = throw UnsupportedOperationException()
	override operator fun get(key: String) = throw UnsupportedOperationException()
	override fun set(key: String, value: JsonValue) = throw UnsupportedOperationException()
	override val jsonType get() = JsonType.Literal
}