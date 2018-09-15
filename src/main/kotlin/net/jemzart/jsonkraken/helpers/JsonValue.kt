package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonValue

@Suppress("NOTHING_TO_INLINE")//Micro optimization on boolean parameter
internal inline fun JsonValue.validateInsert(value: Any?, validateCircularReference: Boolean = true) {
	if (value != null) {
		if (!value.isValidJsonType()) throw InvalidJsonTypeException(value)
		if (validateCircularReference)
			if (value is JsonValue) {
				if (this == value) throw CircularReferenceException(this, value)
				if (value.references(this)) throw CircularReferenceException(this, value)
			}
	}
}