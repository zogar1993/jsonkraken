package net.jemzart.jsonkraken.exceptions


import net.jemzart.jsonkraken.JsonObject

class NoSuchPropertyException(val property: String, val obj: JsonObject): Exception()