package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.toJsonArray
import net.jemzart.jsonkraken.toJsonObject
import net.jemzart.jsonkraken.values.JsonValue
import normalize
import java.lang.NullPointerException

internal fun Any?.purify(): Any? {
	return when (this) {
		is Number -> this.toDouble().normalize()
		is String -> this.validate()
		is Char -> this.toString().purify()
		is JsonValue, is Boolean, null -> this
		is Array<*> -> this.asIterable().purify()
		is Map<*,*> -> this.map {
			val key = it.key?.toString() ?:
			throw NullPointerException("Why are you using null as a key for a map?")
			key to it.value
		}.toMap().toJsonObject()
		is Iterable<*> -> this.toJsonArray()
		else -> throw InvalidJsonTypeException(this)
	}
}

internal fun Any?.purify(container: JsonValue): Any? {
	if (this is JsonValue) {
		if (this == container) throw CircularReferenceException(container, this)
		if (this.references(container)) throw CircularReferenceException(container, this)
		return this
	}
	return this.purify()
}