package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.JsonArray

class NoSuchIndexException(val index: Int, val arr: JsonArray): Exception()