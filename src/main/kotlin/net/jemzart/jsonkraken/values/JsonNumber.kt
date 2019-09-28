package net.jemzart.jsonkraken.values

import java.math.BigDecimal
//TODO validate NaN and infinity
class JsonNumber(number: Number) : JsonValue() {
	val value = bigDecimalFor(number)
	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}

	private fun bigDecimalFor(number: Number): BigDecimal {
		if (number == -0.0) return BigDecimal(0.0)
		if (number.toInt().toDouble() == number) return BigDecimal(number.toInt())
		return BigDecimal(number.toString())//TODO simplify
	}
}