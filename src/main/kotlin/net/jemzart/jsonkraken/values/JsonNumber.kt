package net.jemzart.jsonkraken.values

import java.math.BigDecimal

class JsonNumber(number: Number) : JsonValue() {
	val value = if (number == -0.0) BigDecimal(0.0) else if (number.toInt().toDouble() == number) BigDecimal(number.toInt()) else BigDecimal(number.toString())//TODO simplify

	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}