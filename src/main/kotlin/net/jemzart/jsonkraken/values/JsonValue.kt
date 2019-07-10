package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import kotlin.reflect.KClass

abstract class JsonValue: JsonCasteable by Companion {
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
	inline fun <reified T>cast(): T {
		if (this is JsonNull && null is T && T::class !in casts.keys) return null as T
		if (this.casts.containsKey(T::class))
			return casts.getValue(T::class as KClass<out Any>)(this) as T
		else
			throw InvalidCastException(from = this::class, to = T::class)
	}

	protected companion object: JsonCasteable {
		override val casts = mapOf(
			JsonValue::class to { value: Any -> value },
			Any::class to { value: Any -> value }
		)
	}
}