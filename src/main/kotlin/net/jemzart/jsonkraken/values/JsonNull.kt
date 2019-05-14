package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonNull : JsonValue() {
	@Suppress("UNCHECKED_CAST")
	override fun <T> cast(klass: KClass<*>): T {
		return when(klass){
			JsonNull::class -> this as T
			JsonValue::class -> this as T
			Any::class -> this as T
			else -> null as T
		}
	}
}