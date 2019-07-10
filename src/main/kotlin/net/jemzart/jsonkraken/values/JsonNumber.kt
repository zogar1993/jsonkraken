package net.jemzart.jsonkraken.values

class JsonNumber(number: Number) : JsonValue(), JsonCasteable by Companion {
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
	
	private companion object: JsonCasteable {
		override val casts =
				JsonValue.casts + (JsonNumber::class to { value: Any -> value }) +
						(Byte::class to { value -> (value as JsonNumber).value.toByte() }) +
						(Short::class to { value -> (value as JsonNumber).value.toShort() }) + 
						(Int::class to { value -> (value as JsonNumber).value.toInt() }) + 
						(Long::class to { value -> (value as JsonNumber).value.toLong() }) +
						(Float::class to { value -> (value as JsonNumber).value.toFloat() }) +
						(Double::class to { value -> (value as JsonNumber).value }) +
						(Number::class to { value -> (value as JsonNumber).value }) +
						(Char::class to { value -> (value as JsonNumber).value.toChar() })
	}
}