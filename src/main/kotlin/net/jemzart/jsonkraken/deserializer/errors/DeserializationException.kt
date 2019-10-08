package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used when deserializing a String which is not a valid JSON representation.
 *
 * @property[message] description of the error.
 * @since 2.0
 */
class DeserializationException constructor(message: String) : JsonKrakenException(message){

}