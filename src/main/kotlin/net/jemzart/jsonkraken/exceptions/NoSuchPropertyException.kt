package net.jemzart.jsonkraken.exceptions


import net.jemzart.jsonkraken.values.JsonObject
import java.lang.Exception

class NoSuchPropertyException(val property: String, val obj: JsonObject): Exception()