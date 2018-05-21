package jemzart.kjson.values

import jemzart.kjson.JSON_VALUE

class JsonObject : JsonValue() {
	override val size: Int get() = map.size
	private val map: MutableMap<String, Any?> = mutableMapOf()

	override fun iterator(): Iterator<Pair<String, Any?>> = map.map { it.key to it.value }.iterator()

	override fun <T> get(key: String, shamelessHack: T): T = map[key] as T
	override fun get(key: String): JsonValue = get(key, JSON_VALUE)
	override fun set(key: String, value: Any?){
		map[key] = value
	}

	fun remove(key: String){
		map.remove(key)
	}
}