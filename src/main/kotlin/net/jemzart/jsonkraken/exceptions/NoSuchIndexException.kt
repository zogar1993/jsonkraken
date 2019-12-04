package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonArray
import java.lang.Exception

class NoSuchIndexException(val index: Int, val arr: JsonArray): Exception()