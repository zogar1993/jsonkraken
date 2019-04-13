package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.toJsonValue

/**
 * @constructor empty json object.
 */
class JsonObject() : JsonContainer, Iterable<Pair<JsonString, JsonValue>> {
	/**
	 * @constructor json object filled with [properties].
	 * Pair second values must be of valid types (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach { map[JsonString(it.first)] = it.second.purify() }
	}

	override val size: Int get() = map.size
	private val map: MutableMap<JsonString, JsonValue> = mutableMapOf()

	/**
	 * @return an iterator over all its properties.
	 */
	override fun iterator(): Iterator<Pair<JsonString, JsonValue>> = map.map { it.key to it.value }.iterator()

	override fun get(name: String): JsonValue = map[JsonString(name)] ?: throw Exception()//TODO ver si no da bronce
	override fun set(name: String, value: Any?) {
		map[JsonString(name)] = value.purify(this)
	}

	fun remove(name: String) {
		map.remove(JsonString(name))
	}

	fun exists(name: String): Boolean {
		return map.containsKey(JsonString(name))
	}

	/**
	 * @return a collection with all its property keys.
	 */
	val keys get() = map.keys

	/**
	 * @return a collection with all its property values.
	 */
	val values get() = map.values

	override fun clone(): JsonObject = map.map {
		val value = it.value
		it.key to (if (value is JsonContainer) value.clone() else value)
	}.toMap().toJsonValue() as JsonObject

	internal fun uncheckedSet(name: JsonString, value: Any?) = map.set(name, value.purify())

	override fun references(value: JsonContainer): Boolean = map.values.references(value)
}