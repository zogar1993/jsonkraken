package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonContainer

/**
 * An exception used for operations that would incur in a circular reference.
 *
 * Logically, this can only occur when assigning a JsonValue to another.
 *
 * @property host the name of this group.
 * @property intruder the name of this group.
 */
class CircularReferenceException(val host: JsonContainer,
                                 val intruder: JsonContainer)
	: Exception("Performing the operation would incur in a circular reference.")