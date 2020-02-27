package net.jemzart.jsonkraken.errors.transformation

import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used for when a JsonValue tries to be parsed to an invalid type.
 * See 'Valid Types' for more information. (https://github.com/zogar1993/jsonkraken#valid-types)
 *
 * @property[value] the value of a non permitted type.
 */
class InvalidJsonTypeException(val value: Any)
	: JsonKrakenException("${value::class.simpleName} is not among the permitted types.")