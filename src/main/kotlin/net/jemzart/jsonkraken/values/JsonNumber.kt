package net.jemzart.jsonkraken.values

class JsonNumber(number: Number) : JsonValue() {
	val value = number.toDouble().normalize()

	//turns -0.0 into 0.0 to prevent boxing issues
	private fun Double.normalize() = if (this == -0.0) 0.0 else this

	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}