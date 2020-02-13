package net.jemzart.jsonkraken.exceptions

/**
 * An exception used when the Number to be wrapped by a JsonNumber is not compliant with the JSON specification.
 *
 * @property value invalid number which triggered the exception.
 */
class NonCompliantNumberException(val value: String)
	: JsonKrakenException("Number '$value' is not compliant with the JSON specification.")