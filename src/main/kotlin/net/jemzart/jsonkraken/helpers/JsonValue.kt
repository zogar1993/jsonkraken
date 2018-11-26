package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonValue

@Suppress("NOTHING_TO_INLINE")//Micro optimization on boolean parameter
internal inline fun JsonValue.purify(value: Any?, validateCircularReference: Boolean = true) : Any? {
	if (value == null) {
		return null
	} else {
		return when(value){
			is Number -> {
				val number = value.toDouble()
				if (number == 0.0) 0.0 else number //turns -0.0 into 0.0 to prevent boxing issues
			}
			is String -> {
				value.validate()
				value
			}
			is Boolean -> value
			is Char -> {
				val result = value.toString()
				result.validate()
				result
			}
			is JsonValue -> {
				if (validateCircularReference){
					if (this == value) throw CircularReferenceException(this, value)
					if (value.references(this)) throw CircularReferenceException(this, value)
				}
				value
			}
			else -> throw InvalidJsonTypeException(value)
		}
	}
}