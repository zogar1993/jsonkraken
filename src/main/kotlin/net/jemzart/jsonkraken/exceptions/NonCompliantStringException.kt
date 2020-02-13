package net.jemzart.jsonkraken.exceptions

/**
 * An exception used when the String to be wrapped by a JsonString is not compliant with the JSON specification.
 *
 * @property value invalid string which triggered the exception.
 */
class NonCompliantStringException(val value: String, message: String)
	: JsonKrakenException("String '$value' is not compliant with the JSON specification.\n$message")