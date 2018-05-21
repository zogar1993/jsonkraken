package jemzart.kjson.values

import jemzart.kjson.JSON_VALUE

class JsonObject : JsonValue() {
	override val size: Int get() = map.size
	private val map: MutableMap<String, Any?> = mutableMapOf()

	override fun iterator(): Iterator<Pair<String, Any?>> = map.map { it.key to it.value }.iterator()

	override fun <T> get(name: String, shamelessHack: T): T = map[name] as T
	override fun get(name: String): JsonValue = get(name, JSON_VALUE)
	override fun set(name: String, value: Any?){
		map[name] = value
	}

	fun remove(name: String){
		map.remove(name)
	}
}