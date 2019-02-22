package net.jemzart.jsonkraken.wrappers

import net.jemzart.jsonkraken.exceptions.TokenExpectationException

inline class BoundedString(val value: String){
	@Suppress("NOTHING_TO_INLINE")
	inline operator fun get(index: Int): Char =
		if (index < value.length) value[index]
		else throw TokenExpectationException("Premature end of String")
	@Suppress("NOTHING_TO_INLINE")
	inline fun substring (start: Int, end: Int) = value.substring(start, end)
}