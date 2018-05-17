package jemzart.kjson

import jemzart.kjson.parsers.StringToObjectParser
import jemzart.kjson.values.*

fun emptyJsonObject() = JsonObject(LinkedHashMap())
fun emptyJsonArray() = JsonArray(mutableListOf())
fun jsonTrue() = jsonTrue
fun jsonFalse() = jsonFalse
fun jsonBoolean(value: Boolean) = if (value) jsonTrue else jsonFalse
fun jsonNull() = jsonNull
fun String.toJson(): JsonValue = StringToObjectParser(this).create()

internal val jsonTrue = JsonTrue()
internal val jsonFalse = JsonFalse()
internal val jsonNull = JsonNull()