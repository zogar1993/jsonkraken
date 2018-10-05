package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.toJsonObject

class JsonObject() : JsonValue {
	constructor(vararg properties: Pair<String, Any?>) : this() {
		for (property in properties) {
			val purified = purify(property.second, validateCircularReference = false)
			map[property.first] = purified
		}
	}

	override val size: Int get() = map.size
	private val map: MutableMap<String, Any?> = mutableMapOf()

	override fun iterator(): Iterator<Pair<String, Any?>> = map.map { it.key to it.value }.iterator()
	override fun get(name: String): Any? = map[name]
	override fun set(name: String, value: Any?) {
		map[name] = purify(value)
	}

	override fun remove(name: String) {
		map.remove(name)
	}

	override fun exists(name: String): Boolean {
		return map.containsKey(name)
	}

	val keys get() = map.keys

	val values get() = map.values

	override fun clone(): JsonObject = map.map {
		it.key to if (it.value is JsonValue) (it.value as JsonValue).clone() else it.value
	}.toMap().toJsonObject()

	internal fun uncheckedSet(name: String, value: Any?) = map.set(name, value)

	override fun references(value: JsonValue): Boolean = map.values.references(value)

	override fun get(index: Int): Any? = get(index.toString())
	override fun set(index: Int, value: Any?) = set(index.toString(), value)
	override fun remove(index: Int) = remove(index.toString())
	override fun exists(index: Int): Boolean = exists(index.toString())
}