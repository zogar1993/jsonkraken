package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.wrappers.JsonValueMap
import kotlin.reflect.KClass

/**
 * @constructor empty json object.
 */
class JsonObject() : JsonContainer(), Iterable<Pair<String, JsonValue>> {
	/**
	 * @constructor json object filled with [properties].
	 * Pair second values must be of valid types (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach { map[it.first] = it.second.purify() }
	}

	override val size: Int get() = map.size
	private val map: JsonValueMap = JsonValueMap()

	/**
	 * @return an iterator over all its properties.
	 */
	override fun iterator(): Iterator<Pair<String, JsonValue>> = map.iterator()

	override fun get(name: String): JsonValue = map[name]
	override fun set(name: String, value: Any?) {
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

	override fun clone(): JsonObject = map.map {
		val value = it.second
		it.first to (if (value is JsonContainer) value.clone() else value)
	}.toMap().toJsonValue() as JsonObject

	internal fun uncheckedSet(name: JsonString, value: Any?) = map.set(name.value, value.purify())

	override fun references(value: JsonContainer): Boolean = map.values.references(value)

	override val casts: Map<KClass<*>, (Any)->Any> get() = Companion.casts
	companion object {
		private val casts = JsonContainer.casts + (JsonObject::class to { value: Any -> value })
	}
}