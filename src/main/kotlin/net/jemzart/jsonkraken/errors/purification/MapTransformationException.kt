package net.jemzart.jsonkraken.errors.purification

import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used for failures while transforming a map into a JsonObject.
 *
 * @property[map] the map to be transformed.
 * @property[inner] the inner exception which made the transformation fail.
 */
class MapTransformationException(val map: Map<*, *>, val inner: Throwable) :
	JsonKrakenException("an error occurred while transforming a map into a JsonValue")