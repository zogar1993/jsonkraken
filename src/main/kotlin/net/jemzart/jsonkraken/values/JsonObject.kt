package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.copy
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.helpers.validateJsonStringCompliance

//TODO Update Docs

/**
 * @constructor empty json object.
 */
class JsonObject() : JsonContainer(), Iterable<Pair<String, JsonValue>> {
	/**
	 * @constructor json object filled with [properties].
	 * Pair second values must be of valid types (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach {
			it.first.validateJsonStringCompliance()
			map[it.first] = it.second.purify()
		}
	}

	override val size: Int get() = map.size
	internal val map: MutableMap<String, JsonValue> = mutableMapOf()

	/**
	 * @return an iterator over all its properties.
	 */
	override fun iterator(): Iterator<Pair<String, JsonValue>> = map.map { it.key to it.value }.iterator()

	override fun get(name: String): JsonValue = map.getValue(name)
	override fun set(name: String, value: Any?) {
		name.validateJsonStringCompliance()
		map[name] = value.purify(this)
	}

	fun remove(name: String) {
		map.remove(name)
	}

	/**
	 * @return a collection with all its property keys.
	 */
	val keys get() = map.keys

	/**
	 * @return a collection with all its property values.
	 */
	val values get() = map.values

	override fun clone() = JsonObject(*map { it.first to copy(it.second) }.toTypedArray())

	override fun references(value: JsonContainer): Boolean = map.values.references(value)
}