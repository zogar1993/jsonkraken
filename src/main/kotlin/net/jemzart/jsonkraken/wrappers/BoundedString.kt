package net.jemzart.jsonkraken.wrappers

import net.jemzart.jsonkraken.exceptions.TokenExpectationException

internal inline class BoundedString(val value: String) {
	operator fun get(index: Int): Char =
		if (index < value.length) value[index]
		else throw TokenExpectationException("Premature end of String")

	fun substring(start: Int, end: Int) = value.substring(start, end)
}