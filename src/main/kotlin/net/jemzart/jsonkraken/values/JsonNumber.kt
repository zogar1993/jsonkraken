package net.jemzart.jsonkraken.values

import java.lang.Exception
import kotlin.reflect.KClass

class JsonNumber(number: Number) : JsonValue() {
	val value = number.toDouble().normalize()

	//turns -0.0 into 0.0 to prevent boxing issues
	private fun Double.normalize() = if (this == -0.0) 0.0 else this

	@Suppress("UNCHECKED_CAST")
	override fun <T> cast(klass: KClass<*>): T {
		return when(klass){
			JsonNumber::class -> this as T
			JsonValue::class -> this as T
			Any::class -> this as T
			Char::class -> value.toChar() as T
			Byte::class -> value.toByte() as T
			Short::class -> value.toShort() as T
			Int::class -> value.toInt() as T
			Long::class -> value.toLong() as T
			Float::class -> value.toFloat() as T
			Double::class -> value as T
			Number::class -> value as T
			else -> throw NotImplementedError(klass.toString())
		}
	}

	override fun equals(other: Any?): Boolean {
		val jsonOther = other as? JsonNumber
			?: throw Exception()//TODO Mejorar
		return value == jsonOther.cast<JsonNumber>().value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}