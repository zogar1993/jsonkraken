package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.parsers.ObjectToStringParser
import net.jemzart.jsonkraken.parsers.StringToObjectParser
import net.jemzart.jsonkraken.values.JsonValue

fun String.toJson(): Any? = StringToObjectParser(this).create()
fun Any?.toJsonString(): String = ObjectToStringParser(this).create()
operator fun Any?.get(name: String): Any? =
	if (this is JsonValue) this[name]
	else throw UnsupportedOperationException()
operator fun Any?.get(index: Int): Any? =
	if (this is JsonValue) this[index]
	else throw UnsupportedOperationException()