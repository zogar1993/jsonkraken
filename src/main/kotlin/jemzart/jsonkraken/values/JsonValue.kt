package jemzart.jsonkraken.values

abstract class JsonValue: Iterable<Any?> {
	abstract operator fun get(name: String): Any?
	abstract operator fun set(name: String, value: Any?)
	abstract fun remove (name: String)
	abstract fun exists (name: String): Boolean
	abstract operator fun get(index: Int): Any?
	abstract operator fun set(index: Int, value: Any?)
	abstract fun remove (index: Int)
	abstract fun exists (index: Int): Boolean
	abstract val size: Int
}