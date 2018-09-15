package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonValue

class CircularReferenceException(val host: JsonValue,
                                 val intruder: JsonValue)
	: Exception("Performing the operation would incur in a circular reference.")