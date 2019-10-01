package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used when finding invalid tokens while deserializing.
 *
 * @property message description of the unexpected token.
 */
class DeserializationException(message: String) : JsonKrakenException(message)
//TODO Update Docs