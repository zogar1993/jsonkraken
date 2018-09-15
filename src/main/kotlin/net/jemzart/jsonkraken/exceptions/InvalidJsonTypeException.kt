package net.jemzart.jsonkraken.exceptions

import java.lang.Exception

class InvalidJsonTypeException(val value: Any)
	: Exception("${value::class} is not among the permitted types.")