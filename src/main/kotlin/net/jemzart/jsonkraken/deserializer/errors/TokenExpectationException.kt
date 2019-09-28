package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used when finding invalid tokens when deserializing.
 * JsonValue, null and all primitives are valid types.
 *
 * @property message description of the unexpected token.
 */
class TokenExpectationException(message: String) : JsonKrakenException(message)
//TODO Update Docs