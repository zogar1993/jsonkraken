package net.jemzart.jsonkraken.errors.collections

import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * Used when a property is being extracted from a JsonObject, and it does not possess it.
 *
 * @property[obj] the JsonObject which did not have a pair with said key.
 * @property[key] said key.
 */
class NoSuchPropertyException(val key: String, val obj: JsonObject)
	: JsonKrakenException("No element at key $key")