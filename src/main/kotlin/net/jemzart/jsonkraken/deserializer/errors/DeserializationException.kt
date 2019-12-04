package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.exceptions.JsonKrakenException

/**
 * An exception used when deserializing a String which is not a valid JSON representation.
 *
 * @property[raw] raw data to be deserialized.
 * @property[index] index of the illegal character.
 * @property[detail] information specific to the error.
 * @property[snapshot] visual representation of the error.
 * @since 2.0
 */
open class DeserializationException constructor(
	val index: Int,
	val raw: String,
	val detail: String,
	val snapshot: String) :
	JsonKrakenException("\nError at character $index.\n$detail\n$snapshot")