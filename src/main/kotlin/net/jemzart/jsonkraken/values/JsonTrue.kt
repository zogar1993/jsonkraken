package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonTrue : JsonBoolean() {
	@Suppress("UNCHECKED_CAST")
	override fun <T> cast(klass: KClass<*>): T {
		return when(klass){
			JsonTrue::class -> this as T
			JsonBoolean::class -> this as T
			JsonValue::class -> this as T
			Any::class -> this as T
			Boolean::class -> true as T
			else -> throw NotImplementedError(klass.toString())
		}
	}
}