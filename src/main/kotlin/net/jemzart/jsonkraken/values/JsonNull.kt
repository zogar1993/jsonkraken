package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonNull : JsonValue() {
    override val casts: Map<KClass<*>, (Any)->Any> get() =
        JsonValue.casts + (JsonNull::class to { value: Any -> value })
}