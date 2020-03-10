package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.errors.deserialization.DeserializationException
import net.jemzart.jsonkraken.errors.primitives.NonCompliantNumberException
import net.jemzart.jsonkraken.errors.primitives.NonCompliantStringException
import net.jemzart.jsonkraken.errors.transformation.UnexpectedJsonValueException
import net.jemzart.jsonkraken.errors.purification.ArrayTransformationException
import net.jemzart.jsonkraken.errors.purification.IterableTransformationException
import net.jemzart.jsonkraken.errors.purification.MapTransformationException
import net.jemzart.jsonkraken.purifier.purify
import net.jemzart.jsonkraken.serializer.TemplateSerializer
import net.jemzart.jsonkraken.serializer.SimpleSerializer
import net.jemzart.jsonkraken.serializer.Tabulation

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
	 * @throws [UnexpectedJsonValueException].
	 * @throws [DeserializationException].
	 */
	inline fun <reified T : JsonValue> deserialize(data: String): T {
		val raw = Deserializer(data).create()
		return cast(raw)
	}

	/**
	 * @since 2.0
	 * @param [value] will be converted to its String JSON representation.
	 * @param [tabulation] will add tabulation according to standard predefined templates.
	 * @return a String with the serialized JSON representation of [value].
	 * @throws [NonCompliantStringException].
	 * @throws [NonCompliantNumberException].
	 * @throws [MapTransformationException].
	 * @throws [IterableTransformationException].
	 * @throws [ArrayTransformationException].
	 */
	fun serialize(value: Any?, tabulation: Tabulation = Tabulation.None): String {
		return if (tabulation == Tabulation.None) SimpleSerializer(purify(value)).create()
		else TemplateSerializer(purify(value), tabulation).create()
	}

	/**
	 * @since 2.0
	 * @param [value] will be converted to [T] representation.
	 * @param [T] is the expected representation of [value] .
	 * @return a [T] representation of [value].
	 * @throws [UnexpectedJsonValueException].
	 * @throws [NonCompliantStringException].
	 * @throws [NonCompliantNumberException].
	 * @throws [MapTransformationException].
	 * @throws [IterableTransformationException].
	 * @throws [ArrayTransformationException].
	 */
	inline fun <reified T : JsonValue> transform(value: Any?): T {
		val result = purify(value)
		return cast(result)
	}

	@PublishedApi
	internal inline fun <reified T : JsonValue> cast(result: JsonValue) =
		if (result is T) result else throw UnexpectedJsonValueException(T::class, result::class)
}