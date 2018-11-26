package net.jemzart.jsonkraken.exceptions

/**
 * An exception used when the String added to a JsonValue is not compliant with the JSON specification.
 *
 * @property value invalid string which triggered the exception.
 */
class NonCompliantStringException(val value: String, message: String) : Exception(message)