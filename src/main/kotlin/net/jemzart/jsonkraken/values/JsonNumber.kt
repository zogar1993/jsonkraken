package net.jemzart.jsonkraken.values

import java.lang.Exception
import kotlin.reflect.KClass

class JsonNumber(number: Number) : JsonValue() {
	val value = number.toDouble().normalize()

	//turns -0.0 into 0.0 to prevent boxing issues
	private fun Double.normalize() = if (this == -0.0) 0.0 else this


	override val casts: Map<KClass<*>, (Any)->Any> get() =
		JsonValue.casts + (JsonNumber::class to { value: Any -> value }) +
				(Byte::class to { _ -> value.toByte() }) +
				(Short::class to { _ -> value.toShort() }) +
				(Int::class to { _ -> value.toInt() }) +
				(Long::class to { _ -> value.toLong() }) +
				(Float::class to { _ -> value.toFloat() }) +
				(Double::class to { _ -> value }) +
				(Number::class to { _ -> value }) +
				(Char::class to { _ -> value.toChar() })

	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}