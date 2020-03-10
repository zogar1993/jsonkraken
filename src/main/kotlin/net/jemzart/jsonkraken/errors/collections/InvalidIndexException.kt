package net.jemzart.jsonkraken.errors.collections

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * Used when a String is being used to access a JsonArray, and such String could not be cast to Int.
 *
 * @property[arr] the JsonArray which triggered the exception.
 * @property[value] String value which attempted to be used as an index.
 */
class InvalidIndexException(val value: String, val arr: JsonArray) : JsonKrakenException()