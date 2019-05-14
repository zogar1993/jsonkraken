package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonFalse : JsonBoolean() {
	@Suppress("UNCHECKED_CAST")
	override fun <T> cast(klass: KClass<*>): T {
		return when(klass){
			JsonFalse::class -> this as T
			JsonBoolean::class -> this as T
			JsonValue::class -> this as T
			Any::class -> this as T
			Boolean::class -> false as T
			else -> throw NotImplementedError(klass.toString())
		}
	}
}