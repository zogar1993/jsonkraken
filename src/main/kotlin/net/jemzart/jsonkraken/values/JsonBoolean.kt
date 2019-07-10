package net.jemzart.jsonkraken.values

abstract class JsonBoolean : JsonValue(), JsonCasteable by Companion {
    protected companion object: JsonCasteable {
        override val casts = JsonValue.casts + (JsonBoolean::class to { value: Any -> value })
    }
}