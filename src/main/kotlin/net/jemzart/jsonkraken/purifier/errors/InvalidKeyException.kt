package net.jemzart.jsonkraken.purifier.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * @since 2.0
 * An exception used when attempting to transform a map with a non String key into a JsonObject.
 *
 * @property[value] invalid string which triggered the exception.
 */
class InvalidKeyException(val value: Any?) : JsonKrakenException("value is not a valid key for a JsonObject pair")