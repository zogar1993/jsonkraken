package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

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
	inline fun <reified T>cast(): T {
		if (this is JsonNull && null is T && T::class !in casts.keys) return null as T
		if (casts.containsKey(T::class))
			return casts.getValue(T::class)(this) as T
		else
			throw NotImplementedError(T::class.toString())
	}

	open val casts: Map<KClass<*>, (Any)->Any> get() = Companion.casts;

	protected companion object {
		val casts = mapOf(
			JsonValue::class to { value: Any -> value },
			Any::class to { value: Any -> value }
		)
	}
}