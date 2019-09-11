package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.parsers.Deserializer
import net.jemzart.jsonkraken.parsers.Serializer
import net.jemzart.jsonkraken.values.JsonValue

object JSONKraken {

	/**
	 * @return a JsonValue representation of [data].
	 */
	fun <T: JsonValue>deserialize(data: String): T {
		val raw = Deserializer(data).create()
		val casted = raw as? T
		casted ?: throw Exception()//TODO must make better exception.
		return casted
	}

	fun serialize(value: Any?, formatted: Boolean = false): String {
		return Serializer(value.purify(), formatted).create()
	}
}
/**
 * @return a JsonValue representation of the receiver..
 * @receiver the object to be converted.
 */
fun Any?.toJsonValue(): JsonValue {
	return this.purify()
}