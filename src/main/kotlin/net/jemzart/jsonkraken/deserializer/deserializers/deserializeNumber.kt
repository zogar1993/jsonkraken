package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.helpers.simplifyJsonNumber

internal fun Deserializer.deserializeNumberStartingWithZero(): JsonNumber {
	val start = index
	zero()
	return deserializeNumber(start)
}

internal fun Deserializer.deserializeNumberLowerThanZero(): JsonNumber {
	val start = index
	minus()
	return deserializeNumber(start)
}

internal fun Deserializer.deserializeNumberEqualOrHigherThanOne(): JsonNumber {
	val start = index
	oneToNine()
	return deserializeNumber(start)
}

private fun Deserializer.minus() {
	advance() //skip -
	when (val char = peek()) {
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateInclusion(char, digits)
	}
}

private fun Deserializer.zero() {
	advance() //skip 0
	if (isAtEnd()) return
	when (peek()) {
		'.' -> dot()
		'e', 'E' -> e()
	}
}

private fun Deserializer.oneToNine() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		'.' -> dot()
		'e', 'E' -> e()
		in '0'..'9' -> firstDigitLoop()
	}
}

private fun Deserializer.dot() {
	advance() //skip .
	when (val char = peek()) {
		in '0'..'9' -> secondDigitLoop()
		else -> validateInclusion(char, digits)
	}
}

private fun Deserializer.e() {
	advance() //skip e or E
	match('+') || match('-')
	if (peek() in '0'..'9') thirdDigitLoop()
	else validateInclusion(peek(), digits + '+' + '-')
}

//before dot
private tailrec fun Deserializer.firstDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		'.' -> dot()
		'e', 'E' -> e()
		in '0'..'9' -> firstDigitLoop()
	}
}

//after dot and before e
private tailrec fun Deserializer.secondDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		'e', 'E' -> e()
		in '0'..'9' -> secondDigitLoop()
	}
}

//after e
private tailrec fun Deserializer.thirdDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	if (peek() in '0'..'9') thirdDigitLoop()
}

private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

private fun Deserializer.deserializeNumber(start: Int): JsonNumber {
	val jsonNumber = JsonNumber()
	jsonNumber.value = simplifyJsonNumber(raw.substring(start, index))
	return jsonNumber
}