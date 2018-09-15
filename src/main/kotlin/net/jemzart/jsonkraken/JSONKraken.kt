package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.parsers.ObjectToStringParser
import net.jemzart.jsonkraken.parsers.StringToObjectParser
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonValue

fun Iterable<*>.toJsonArray(): JsonArray {
	val jsonArray = JsonArray()
	for (item in this) jsonArray.add(item)
	return jsonArray
}

fun Map<String, *>.toJsonObject(): JsonObject {
	val jsonObject = JsonObject()
	for (pair in this) jsonObject[pair.key] = pair.value
	return jsonObject
}

fun String.toJson(): Any? = StringToObjectParser(this).create()
fun Any?.toJsonString(): String = ObjectToStringParser(this).create()

operator fun Any?.get(name: String): Any? =
	if (this is JsonValue) this[name]
	else throw UnsupportedOperationException()

operator fun Any?.get(index: Int): Any? =
	if (this is JsonValue) this[index]
	else throw UnsupportedOperationException()

operator fun Any?.set(name: String, value: Any?) =
	if (this is JsonValue) this[name] = value
	else throw UnsupportedOperationException()

operator fun Any?.set(index: Int, value: Any?) =
	if (this is JsonValue) this[index] = value
	else throw UnsupportedOperationException()