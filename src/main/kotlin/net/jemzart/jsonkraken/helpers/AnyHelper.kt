package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonValue
import normalize

internal fun Any?.purify(): Any? {
	return when (this) {
		is Number -> {
			val number = this.toDouble()
			number.normalize()
		}
		is String -> this.validate()
		is Char -> this.validate()
		is JsonValue, is Boolean, null -> this
		else -> throw InvalidJsonTypeException(this)
	}
}

internal fun Any?.purify(container: JsonValue): Any? {
	if (this is JsonValue) {
		if (container == this) throw CircularReferenceException(container, this)
		if (this.references(container)) throw CircularReferenceException(container, this)
		return this
	}
	return this.purify()
}