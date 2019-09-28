package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateIsDecimal
import net.jemzart.jsonkraken.values.JsonNumber

const val PARSING_NUMBER = "parsing number"
internal fun Deserializer.deserializeNumber(): JsonNumber {
	val start = index
	when (peek()) {
		'-' -> minus()
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateIsDecimal(peek(), PARSING_NUMBER)
	}
	val value = raw.substring(start, index).toDouble()//TODO Romeo debe morir
	return JsonNumber(value)
}


private fun Deserializer.minus() {
	advance() //skip -
	when (peek()) {
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateIsDecimal(peek(), PARSING_NUMBER)
	}
}

private fun Deserializer.dot() {
	advance() //skip .
	when (peek()) {
		in '0'..'9' -> secondDigitLoop()
		else -> validateIsDecimal(peek(), PARSING_NUMBER)
	}
}

private fun Deserializer.e() {
	advance() //skip e or E
	if (peek() == '+' || peek() == '-') advance() //skip + or -
	if (peek() in '0'..'9') thirdDigitLoop()
	else validateIsDecimal(peek(), PARSING_NUMBER)
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

private tailrec fun Deserializer.firstDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		'.' -> dot()
		'e', 'E' -> e()
		in '0'..'9' -> firstDigitLoop()
	}
}

private tailrec fun Deserializer.secondDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		'e', 'E' -> e()
		in '0'..'9' -> secondDigitLoop()
	}
}

private tailrec fun Deserializer.thirdDigitLoop() {
	advance() //skip digit
	if (isAtEnd()) return
	when (peek()) {
		in '0'..'9' -> thirdDigitLoop()
	}
}