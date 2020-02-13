package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.exceptions.NonCompliantNumberException

private val regex = Regex("""-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?""")
internal fun throwIfIsNotAJsonCompliantNumber(number: String) {
	if (!regex.matches(number))
		throw NonCompliantNumberException(number)
}

internal fun simplifyJsonNumber(number: String): String {
	val indexOfDot = number.indexOf('.')
	if (indexOfDot == -1) return cleanIfNegativeZero(number)
	val integer = number.substring(0, indexOfDot)
	val decimal = number.substring(indexOfDot + 1)
	if (decimal.all { it == '0' }) return cleanIfNegativeZero(integer)
	return number
}

private fun cleanIfNegativeZero(integer: String) = if (integer == "-0") "0" else integer