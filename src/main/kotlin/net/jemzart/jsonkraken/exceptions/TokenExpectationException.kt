package net.jemzart.jsonkraken.exceptions

/**
 * An exception used when finding invalid tokens when deserializing.
 * JsonValue, null and all primitives are valid types.
 *
 * @property message description of the unexpected token.
 */
class TokenExpectationException(message: String) : JsonKrakenException(message)