package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonTrue : JsonBoolean() {
	override val casts: Map<KClass<*>, (Any)->Any> get() =
		JsonBoolean.casts + (JsonTrue::class to { value: Any -> value }) + (Boolean::class to { _ -> true })
}