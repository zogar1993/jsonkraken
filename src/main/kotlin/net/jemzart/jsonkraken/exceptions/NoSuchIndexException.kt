package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import java.lang.Exception

class NoSuchIndexException(val index: Int, val arr: JsonArray): Exception()