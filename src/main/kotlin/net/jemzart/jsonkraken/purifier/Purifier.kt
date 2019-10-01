package net.jemzart.jsonkraken.purifier

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.exceptions.NonCompliantStringException
import net.jemzart.jsonkraken.values.*

@PublishedApi
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
		is Map<*, *> -> {
			val jsonObject = JsonObject()
			this.forEach {
				val key = it.key?.toString()
				key ?: throw NonCompliantStringException(null, "'null' is not a valid JsonObject key")
				jsonObject[key] = it.value
			}
			jsonObject
		}
		//todo see if taking control of iterative transformations is worth the troublr to better error handling
		is Iterable<*> -> {
			val jsonArray = JsonArray()
			this.forEach { jsonArray.add(it) }
			jsonArray
		}
		else -> throw InvalidJsonTypeException(this)
	}
}