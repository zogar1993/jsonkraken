package net.jemzart.jsonkraken.exceptions

/**
 * @since 2.0
 * An exception used for operations that try to make use of non permitted types.
 * JsonValue, null and all primitives are valid types.
 *
 * @property value the value of a non permitted type.
 */
class InvalidJsonTypeException(val value: Any)
	: JsonKrakenException("${value::class.simpleName} is not among the permitted types.")