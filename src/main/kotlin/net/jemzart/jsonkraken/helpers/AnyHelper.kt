package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonValue
import normalize
import java.lang.NullPointerException

internal fun Any?.purify(): Any? {
	return when (this) {
		is Number -> {
			val number = this.toDouble()
			number.normalize()
		}
		is String -> this.validate()
		is Char -> this.validate()
		is JsonValue, is Boolean, null -> this
		is Map<*,*> -> {
			val obj = JsonObject()
			this.forEach {
				val key = it.key?.toString() ?:
				throw NullPointerException("Why do you use null as a key for a map?")
				val value = it.value
				obj[key] = value
			}
			obj
		}
		is Iterable<*> -> {
			val arr = JsonArray()
			this.forEach { arr.add(it) }
			arr
		}
		is Array<*> -> {
			val arr = JsonArray()
			this.forEach { arr.add(it) }
			arr
		}
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