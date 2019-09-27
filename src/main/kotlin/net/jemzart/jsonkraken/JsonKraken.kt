package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.parsers.Deserializer
import net.jemzart.jsonkraken.parsers.Serializer
import net.jemzart.jsonkraken.values.JsonValue

object JsonKraken {
	/**
	 * @return a JsonValue representation of [data].
	 */
	inline fun <reified T: JsonValue>deserialize(data: String): T {
		val raw = Deserializer(data).create()
		return cast(raw)
	}

	fun serialize(value: Any?, formatted: Boolean = false): String {
		return Serializer(value.purify(), formatted).create()
	}

	/**
	 * @return a JsonValue representation of [value].
	 * @receiver the object to be converted.
	 */
	inline fun <reified T: JsonValue> transform(value: Any?): T {
		val result = value.purify()
		return cast(result)
	}

	@PublishedApi
	internal inline fun <reified T: JsonValue> cast(result: JsonValue) =
		if (result is T) result else throw InvalidCastException(result::class, T::class)
	//TODO Dunno if use the same for unboxing and cast
}