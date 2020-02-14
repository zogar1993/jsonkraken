package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.JsonArray

/**
 * Used when an item from an index is being extracted from a JsonArray, and it does not possess it.
 *
 * @property arr the JsonArray which did not have an item in required index.
 * @property index said index.
 */
class NoSuchIndexException(val index: Int, val arr: JsonArray): Exception()