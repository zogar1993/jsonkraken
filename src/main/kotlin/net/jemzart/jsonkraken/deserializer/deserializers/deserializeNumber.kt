package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.values.JsonNumber
import java.math.BigDecimal

internal fun Deserializer.deserializeNumber(): JsonNumber {
	val start = index
	when (val char = peek()) {
		'-' -> minus()
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateInclusion(char, digits + '-')
	}
	val json = JsonNumber()
	json.value = BigDecimal(raw.substring(start, index))//TODO Mugre
	return json
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