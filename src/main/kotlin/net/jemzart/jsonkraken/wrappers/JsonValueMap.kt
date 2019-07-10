package net.jemzart.jsonkraken.wrappers

import net.jemzart.jsonkraken.values.JsonString
import net.jemzart.jsonkraken.values.JsonValue

internal class JsonValueMap : Iterable<Pair<String, JsonValue>> {
	private val map = mutableMapOf<String, JsonValue>()
	override fun iterator() = map.map { it.key to it.value }.iterator()
	val size get() = map.size
	val values get() = map.values
	val keys get() = map.keys

	operator fun get(key: String): JsonValue {
		return map.getValue(key)
	}

	operator fun set(key: String, value: JsonValue) {
		JsonString(key)//TODO Mejorar, no hace falta construir JsonString
		map[key] = value
	}

	fun remove(name: String) {
		map.remove(name)
	}

	fun containsKey(name: String): Boolean {
		return map.containsKey(name)
	}
}