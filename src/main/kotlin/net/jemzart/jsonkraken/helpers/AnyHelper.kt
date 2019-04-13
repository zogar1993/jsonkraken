package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.values.*

internal fun Any?.purify(): JsonValue {
	return when (this) {
		is Number -> JsonNumber(this)
		is String -> JsonString(this)
		is Char -> this.toString().purify()
		null -> JsonNull
		true -> JsonTrue
		false -> JsonFalse
		is JsonValue -> this
		is Array<*> -> this.asIterable().purify()
		is Map<*, *> -> this.map {
			val key = it.key?.toString() ?: throw NullPointerException("Why are you using null as a key for a map?")
			key to it.value
		}.toMap().toJsonValue()
		is Iterable<*> -> this.toJsonValue()
		else -> throw InvalidJsonTypeException(this)
	}
}

internal fun Any?.purify(container: JsonContainer): JsonValue {
	if (this is JsonContainer) {
		if (this == container) throw CircularReferenceException(container, this)
		if (this.references(container)) throw CircularReferenceException(container, this)
		return this
	}
	return this.purify()
}