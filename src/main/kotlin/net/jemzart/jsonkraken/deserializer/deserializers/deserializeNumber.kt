package net.jemzart.jsonkraken.deserializer.deserializers

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.deserializer.validators.validateInclusion
import net.jemzart.jsonkraken.values.JsonNumber
import java.math.BigDecimal

internal fun Deserializer.deserializeNumber(): JsonNumber {
	val start = index
	when (peek()) {
		'-' -> minus()
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateInclusion(peek(), digits + '-')
	}
	val json = JsonNumber()
	json.value = BigDecimal(raw.substring(start, index))
	return json
}


private fun Deserializer.minus() {
	advance() //skip -
	when (peek()) {
		'0' -> zero()
		in '1'..'9' -> oneToNine()
		else -> validateInclusion(peek(), digits)
	}
}

private fun Deserializer.dot() {
	advance() //skip .
	when (peek()) {
		in '0'..'9' -> secondDigitLoop()
		else -> validateInclusion(peek(), digits)
	}
}

private fun Deserializer.e() {
	advance() //skip e or E
	if (peek() == '+' || peek() == '-') advance() //skip + or -
	if (peek() in '0'..'9') thirdDigitLoop()
	else validateInclusion(peek(), digits + '+' + '-')
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
	when (peek()) {
		in '0'..'9' -> thirdDigitLoop()
	}
}

private val digits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')