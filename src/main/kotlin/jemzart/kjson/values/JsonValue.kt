package jemzart.kjson.values

abstract class JsonValue: Iterable<Any?> {
	open operator fun get(name: String): JsonValue = throw UnsupportedOperationException()
	open operator fun <T> get(name: String, shamelessHack: T): T = throw UnsupportedOperationException()
	open operator fun set(name: String, value: Any?): Unit = throw UnsupportedOperationException()
	open fun remove (name: String): Unit = throw UnsupportedOperationException()
	open operator fun get(index: Int): JsonValue = throw UnsupportedOperationException()
	open operator fun <T> get(index: Int, shamelessHack: T): T = throw UnsupportedOperationException()
	open operator fun set(index: Int, value: Any?): Unit = throw UnsupportedOperationException()
	open fun remove (index: Int): Unit = throw UnsupportedOperationException()
	abstract val size: Int
}