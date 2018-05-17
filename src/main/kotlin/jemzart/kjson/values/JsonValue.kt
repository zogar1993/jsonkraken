package jemzart.kjson.values

interface JsonValue: Iterable<JsonValue>{
	val value: Any?
	val jsonType: JsonType
	operator fun get(key: String): JsonValue
	operator fun set(key: String, value: JsonValue)
}