package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.helpers.isNullable

abstract class JsonValue {
	/**
	 * @return the value of the property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	open operator fun get(name: String): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in a property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	open operator fun set(name: String, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @return the element at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	open operator fun get(index: Int): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in the selected [index].
	 * if JsonObject, [index] works as a property name.
	 */
	open operator fun set(index: Int, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @return self if possible, otherwise tries to create an equivalent in T.
	 */
	inline fun <reified T> cast(): T {
		when (this) {
			is JsonTrue -> return true as T
			is JsonFalse -> return false as T
			is JsonNull -> if (isNullable<T>()) return null as T
			is JsonString -> when (T::class) {
				CharSequence::class,
				String::class,
				Any::class -> return this.value as T
			}
			is JsonNumber -> when (T::class) {
				Byte::class -> return this.value.toByte() as T
				Short::class -> return this.value.toShort() as T
				Int::class -> return this.value.toInt() as T
				Long::class -> return this.value.toLong() as T
				Float::class -> return this.value.toFloat() as T
				Double::class -> return this.value.toDouble() as T
				Number::class -> return this.value as T
				Any::class -> return this as T
			}
		}
		throw InvalidCastException(from = this::class, to = T::class)
	}
	//todo validations for booleans
}