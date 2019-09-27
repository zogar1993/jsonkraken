package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.parsers.Deserializer
import net.jemzart.jsonkraken.parsers.Serializer
import net.jemzart.jsonkraken.values.JsonValue
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

object JsonKraken {
	/**
	 * @return a JsonValue representation of [data].
	 */
	inline fun <reified T: JsonValue>deserialize(data: String): T = deserialize(data, T::class)

	/**
	 * @return a JsonValue representation of [data].
	 */
	fun <T: JsonValue>deserialize(data: String, kclass: KClass<T>): T {
		val raw = Deserializer(data).create()
		return cast(raw, kclass)
	}

	fun serialize(value: Any?, formatted: Boolean = false): String {
		return Serializer(value.purify(), formatted).create()
	}

	/**
	 * @return a JsonValue representation of [value].
	 * @receiver the object to be converted.
	 */
	inline fun <reified T: JsonValue> transform(value: Any?): T = transform(value, T::class)

	/**
	 * @return a JsonValue representation of [value].
	 * @receiver the object to be converted.
	 */
	fun <T: JsonValue> transform(value: Any?, kclass: KClass<T>): T {
		val result = value.purify()
		return cast(result, kclass)
	}

	@Suppress("UNCHECKED_CAST")
	private fun <T: JsonValue> cast(result: JsonValue, kclass: KClass<T>) =
		if (kclass.isSuperclassOf(result::class)) result as T else throw InvalidCastException(result::class, kclass)
}