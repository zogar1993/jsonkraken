package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonValue
import java.lang.Exception

class CircularReferenceException(val host: JsonValue,
                                 val intruder: JsonValue)
	: Exception("Performing the operation would incur in a circular reference.")