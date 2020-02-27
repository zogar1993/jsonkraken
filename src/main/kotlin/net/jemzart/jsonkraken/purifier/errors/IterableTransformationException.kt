package net.jemzart.jsonkraken.purifier.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * @since 2.0
 * An exception used for failures while transforming an iterable into a JsonArray.
 *
 * @property[iterable] the iterable to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 */
class IterableTransformationException(val iterable: Iterable<*>, val inner: Throwable) :
	JsonKrakenException("an error occurred while transforming an iterable into a JsonArray")