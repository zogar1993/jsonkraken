package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

abstract class JsonBoolean : JsonValue() {
    override val casts: Map<KClass<*>, (Any)->Any> get() = Companion.casts
    protected companion object {
        val casts = JsonValue.casts + (JsonBoolean::class to { value: Any -> value })
    }
}