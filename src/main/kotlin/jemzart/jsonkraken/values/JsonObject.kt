package jemzart.jsonkraken.values

import jemzart.jsonkraken.JSON_VALUE

class JsonObject : JsonValue() {
	override val size: Int get() = map.size
	private val map: MutableMap<String, Any?> = mutableMapOf()

	override fun iterator(): Iterator<Pair<String, Any?>> = map.map { it.key to it.value }.iterator()
	@Suppress("UNCHECKED_CAST")
	override fun <T> get(name: String, shamelessHack: T): T = map[name] as T
	override fun get(name: String): JsonValue = get(name, JSON_VALUE)
	override fun set(name: String, value: Any?){
		map[name] = value
	}

	override fun get(index: Int): JsonValue = get(index.toString())
	override fun <T> get(index: Int, shamelessHack: T): T = get(index.toString(), shamelessHack)
	override fun set(index: Int, value: Any?) = set(index.toString(), value)

	override fun remove(name: String){
		map.remove(name)
	}
}