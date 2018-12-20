package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonValue

internal fun Any?.purify(): Any? {
	return when (this) {
		null -> null
		is Number -> {
			val number = this.toDouble()
			if (number == 0.0) 0.0 else number //turns -0.0 into 0.0 to prevent boxing issues
		}
		is String -> {
			this.validate()
			this
		}
		is Char -> {
			val result = this.toString()
			result.validate()
			result
		}
		is Boolean -> this
		is JsonValue -> this
		else -> throw InvalidJsonTypeException(this)
	}
}

internal fun Any?.purify(container: JsonValue): Any? {
	if (this is JsonValue) {
		if (container == this) throw CircularReferenceException(container, this)
		if (this.references(container)) throw CircularReferenceException(container, this)
	}
	return this.purify()
}