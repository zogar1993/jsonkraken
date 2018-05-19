package jemzart.kjson.values

abstract class JsonValue: Iterable<Any?> {
	open operator fun get(key: String): JsonValue = throw UnsupportedOperationException()
	open operator  fun <T> get(key: String, shamelessHack: T): T = throw UnsupportedOperationException()
	open operator  fun set(key: String, value: Any?): Unit = throw UnsupportedOperationException()
	open operator  fun get(index: Int): JsonValue = throw UnsupportedOperationException()
	open operator  fun <T> get(index: Int, shamelessHack: T): T = throw UnsupportedOperationException()
	open operator  fun set(index: Int, value: Any?): Unit = throw UnsupportedOperationException()
	abstract val size: Int
}