package net.jemzart.jsonkraken.errors.purification

import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used for failures while transforming an iterable into a JsonArray.
 *
 * @property[iterable] the iterable to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 */
class IterableTransformationException(val iterable: Iterable<*>, val inner: Throwable) :
	JsonKrakenException("an error occurred while transforming an iterable into a JsonArray")