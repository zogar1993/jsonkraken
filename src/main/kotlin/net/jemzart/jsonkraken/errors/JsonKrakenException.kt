package net.jemzart.jsonkraken.errors

/**
 * @since 2.0
 * Every internal exception from the library should inherit from this one.
 *
 * @property[message] description of the exception.
 */
abstract class JsonKrakenException(message: String) : Exception(message)