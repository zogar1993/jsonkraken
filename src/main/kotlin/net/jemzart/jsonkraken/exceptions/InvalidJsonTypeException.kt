package net.jemzart.jsonkraken.exceptions

class InvalidJsonTypeException(val value: Any)
	: Exception("${value::class} is not among the permitted types.")