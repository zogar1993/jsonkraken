package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonFalse : JsonBoolean() {
	override val casts: Map<KClass<*>, (Any)->Any> get() =
		JsonBoolean.casts + (JsonFalse::class to { value: Any -> value }) + (Boolean::class to { _ -> false })
}