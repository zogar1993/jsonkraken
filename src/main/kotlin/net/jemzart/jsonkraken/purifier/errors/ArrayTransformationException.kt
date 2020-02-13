package net.jemzart.jsonkraken.purifier.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used for failures while transforming an array into a JsonArray.
 *
 * @property[array] the iterable to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 * @since 2.0
 */
class ArrayTransformationException(val array: Array<*>, val inner: Throwable):
		JsonKrakenException("an error occurred while transforming an array into a JsonArray")