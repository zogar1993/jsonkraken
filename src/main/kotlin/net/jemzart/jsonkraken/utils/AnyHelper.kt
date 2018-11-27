package net.jemzart.jsonkraken.utils

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.validate
import net.jemzart.jsonkraken.values.JsonValue

@Suppress("NOTHING_TO_INLINE")//Micro optimization on boolean condition
internal inline fun Any?.purify(container: JsonValue? = null) : Any? {
	if (this == null) {
		return null
	} else {
		return when(this){
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
			is JsonValue -> {
				if (container != null){
					if (container == this) throw CircularReferenceException(container, this)
					if (this.references(container)) throw CircularReferenceException(container, this)
				}
				this
			}
			else -> throw InvalidJsonTypeException(this)
		}
	}
}