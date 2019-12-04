package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used when deserializing a String which is not a valid JSON representation.
 *
 * @property[message] description of the error.
 * @since 2.0
 */
open class DeserializationException constructor(
	val index: Int,
	val raw: String,
	val detail: String,
	val snapshot: String) :
	JsonKrakenException("\nError at character $index.\n$detail\n$snapshot")

//TODO update docs