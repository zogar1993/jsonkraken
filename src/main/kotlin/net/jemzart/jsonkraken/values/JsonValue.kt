package net.jemzart.jsonkraken.values

interface JsonValue : Iterable<Any?> {
	operator fun get(name: String): Any?
	operator fun set(name: String, value: Any?)
	fun remove(name: String)
	fun exists(name: String): Boolean
	operator fun get(index: Int): Any?
	operator fun set(index: Int, value: Any?)
	fun remove(index: Int)
	fun exists(index: Int): Boolean
	fun clone(): JsonValue
	val size: Int
	fun references(value: JsonValue) : Boolean
}