package net.jemzart.jsonkraken.errors.purification

import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used for failures while transforming an array into a JsonArray.
 *
 * @property[array] the iterable to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 */
class ArrayTransformationException(val array: Array<*>, val inner: Throwable) :
	JsonKrakenException("an error occurred while transforming an array into a JsonArray")