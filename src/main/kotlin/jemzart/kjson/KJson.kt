package jemzart.kjson

import jemzart.kjson.parsers.StringToObjectParser
import jemzart.kjson.values.*

fun jsonObject() = JsonObject()
fun jsonArray() = JsonArray()

val STRING: String = ""
val INTEGER: Int = 0
val BOOLEAN: Boolean = false
val DOUBLE: Double = 0.0
val NULLABLE_STRING: String? = ""
val NULLABLE_INTEGER: Int? = 0
val NULLABLE_BOOLEAN: Boolean? = false
val NULLABLE_DOUBLE: Double? = 0.0

fun String.toJson(): Any? = StringToObjectParser(this).create()