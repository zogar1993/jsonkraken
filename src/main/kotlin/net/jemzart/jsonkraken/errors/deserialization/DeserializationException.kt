package net.jemzart.jsonkraken.errors.deserialization

import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used when deserializing a String which is not a valid JSON representation.
 *
 * @property[raw] raw data to be deserialized.
 * @property[index] index of the illegal character.
 * @property[detail] information specific to the error.
 * @property[snapshot] visual representation of the error.
 */
open class DeserializationException constructor(
	val index: Int,
	val raw: String,
	val detail: String,
	val snapshot: String) :
	JsonKrakenException("\nError at character $index.\n$detail\n$snapshot")