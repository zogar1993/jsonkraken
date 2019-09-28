package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.copy
import net.jemzart.jsonkraken.purifier.purify
import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantString

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
		properties.forEach { (name, value) ->
			throwIfIsNotAJsonCompliantString(name)
			map[name] = value.purify()
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
		throwIfIsNotAJsonCompliantString(name)
		val purified = value.purify()
		throwIfHasAReferenceOnMe(purified)
		map[name] = purified
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

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(map.values)
}