package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.parsers.Deserializer
import net.jemzart.jsonkraken.parsers.Serializer
import net.jemzart.jsonkraken.values.JsonValue

/**
 * @return a JsonValue representation of the receiver..
 * @receiver the object to be converted.
 */
fun Any?.toJsonValue(): JsonValue {
	return this.purify()
}

/**
 * @return a JsonValue representation of the receiver.
 * @receiver raw json data.
 */
fun String.jsonDeserialize(): JsonValue = Deserializer(this).create()

/**
 * @return a JsonValue representation of the receiver.
 * @receiver raw json data.
 */
fun Any?.jsonSerialize(formatted: Boolean = false): String = Serializer(this, formatted).create()