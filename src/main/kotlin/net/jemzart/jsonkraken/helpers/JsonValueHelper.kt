package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.JsonContainer
import net.jemzart.jsonkraken.JsonValue
import net.jemzart.jsonkraken.errors.transformation.InvalidCastException

internal fun copy(value: JsonValue) = if (value is JsonContainer) value.clone() else value

@PublishedApi
internal inline fun <reified T> JsonValue.stringOrThrow(value: String): T {
	return when (T::class) {
		CharSequence::class, String::class, Any::class -> value as T
		else -> throwInvalidCastException<T>()
	}
}

@PublishedApi
internal inline fun <reified T> JsonValue.numberOrThrow(value: String): T {
	return when (T::class) {
		Byte::class -> value.toByte()
		Short::class -> value.toShort()
		Int::class -> value.toInt()
		Long::class -> value.toLong()
		Float::class -> value.toFloat()
		Double::class -> value.toDouble()
		Any::class -> value
		else -> throwInvalidCastException<T>()
	} as T
}

@PublishedApi
internal inline fun <reified T> JsonValue.booleanOrThrow(value: Boolean): T {
	return when (T::class) {
		Boolean::class, Any::class -> value as T
		else -> throwInvalidCastException<T>()
	}
}

@PublishedApi
internal inline fun <reified T> JsonValue.nullOrThrow() =
	if (isNullable<T>()) null as T else throwInvalidCastException<T>()

@PublishedApi
internal inline fun <reified T> JsonValue.throwInvalidCastException(): Nothing =
	throw InvalidCastException(from = this::class, to = T::class)