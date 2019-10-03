package net.jemzart.jsonkraken.purifier

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.exceptions.JsonKrakenException
import net.jemzart.jsonkraken.values.*

@PublishedApi
internal fun purify(thing: Any?): JsonValue {
	return when (thing) {
		is Number -> JsonNumber(thing)
		is String -> JsonString(thing)
		is Char -> purify(thing.toString())
		null -> JsonNull
		true -> JsonTrue
		false -> JsonFalse
		is JsonValue -> thing
		is Array<*> -> purifyArray(thing)
		is Map<*, *> -> purifyMap(thing)
		is Iterable<*> -> purifyIterable(thing)
		else -> throw InvalidJsonTypeException(thing)
	}
}

private fun purifyIterable(iterable: Iterable<*>): JsonArray {
	val jsonArray = JsonArray()
	iterable.forEach {
		val item = runCatching { purify(it) }.onFailure { throw IterableTransformationException(iterable, it) }
		jsonArray.add(item.getOrThrow())
	}
	return jsonArray
}

private fun purifyArray(array: Array<*>): JsonArray {
	val jsonArray = JsonArray()
	array.forEach {
		val item = runCatching { purify(it) }.onFailure { throw ArrayTransformationException(array, it) }
		jsonArray.add(item.getOrThrow())
	}
	return jsonArray
}

private fun purifyMap(map: Map<*, *>): JsonObject {
	val jsonObject = JsonObject()
	map.forEach {
		val key = runCatching { purifyKey(it.key) }.onFailure { throw MapTransformationException(map, it) }
		val value = runCatching { purify(it.value) }.onFailure { throw MapTransformationException(map, it) }
		jsonObject[key.getOrThrow()] = value.getOrThrow()
	}
	return jsonObject
}

internal fun purifyKey(key: Any?) =	if (key !is String) throw InvalidKeyException(key) else key

class InvalidKeyException(val value: Any?):
		JsonKrakenException("value is not a valid key for a JsonObject pair")
class MapTransformationException(val map: Map<*,*>, val inner: Throwable):
		JsonKrakenException("an error occurred while tranforming a map into a JsonValue")
class IterableTransformationException(val iterable: Iterable<*>, val inner: Throwable):
		JsonKrakenException("an error occurred while transforming an iterable into a JsonArray")
class ArrayTransformationException(val array: Array<*>, val inner: Throwable):
		JsonKrakenException("an error occurred while transforming an array into a JsonArray")