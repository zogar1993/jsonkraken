package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.exceptions.UnexpectedJsonValueException
import net.jemzart.jsonkraken.purifier.purify
import net.jemzart.jsonkraken.serializer.FormattedSerializer
import net.jemzart.jsonkraken.serializer.SimpleSerializer

/**
 * @since 2.0
 * Contains all main operations operations: serialization, deserialization and transformation.
 */
object JsonKraken {
	/**
	 * @since 2.0
	 * @param [data] is a raw String representation of a JSON.
	 * @param [T] is the expected representation of [data].
	 * @return a [T] representation of [data].
	 */
	inline fun <reified T : JsonValue> deserialize(data: String): T {
		val raw = Deserializer(data).create()
		return cast(raw)
	}

	/**
	 * @since 2.0
	 * @param [value] will be converted to its String JSON representation.
	 * @param [formatted] will format the resulting JSON representation if true.
	 * @return a String with the serialized JSON representation of [value].
	 * @throws [InvalidCastException] See 'Valid Types' for more information.
	 */
	fun serialize(value: Any?, formatted: Boolean = false): String {
		return if (formatted) FormattedSerializer(purify(value)).create()
		else SimpleSerializer(purify(value)).create()
	}

	/**
	 * @since 2.0
	 * @param [value] will be converted to [T] representation.
	 * @param [T] is the expected representation of [value] .
	 * @return a [T] representation of [value].
	 * @throws [InvalidCastException] See 'Valid Types' for more information.
	 */
	inline fun <reified T : JsonValue> transform(value: Any?): T {
		val result = purify(value)
		return cast(result)
	}

	@PublishedApi
	internal inline fun <reified T : JsonValue> cast(result: JsonValue) =
		if (result is T) result else throw UnexpectedJsonValueException(T::class, result::class)
}

//TODO UPDATE README
//TODO UPDATE THROWS