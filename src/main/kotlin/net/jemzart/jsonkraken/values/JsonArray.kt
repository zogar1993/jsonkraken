package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.toJsonValue
import kotlin.reflect.KClass

/**
 * @constructor empty json array.
 */
class JsonArray() : JsonContainer(), Iterable<JsonValue>, JsonCasteable by Companion {
	private fun Int.reversible() = if (this < 0) list.size + this else this

	/**
	 * @constructor json array filled with [items].
	 * Items must be of valid types (See 'Valid Types').
	 */
	constructor(vararg items: Any?) : this() {
		for (item in items) {
			val purified = item.purify()
			list.add(purified)
		}
	}

	override val size: Int get() = list.size
	private val list: MutableList<JsonValue> = mutableListOf()

	/**
	 * @return an iterator over all its items.
	 */
	override fun iterator(): Iterator<JsonValue> = list.iterator()

	override fun get(index: Int): JsonValue = list[index.reversible()]

	override fun set(index: Int, value: Any?) {
		val purified = value.purify(this)
		for (i in list.size..index) list.add(JsonNull)
		list[index.reversible()] = purified
	}

	fun remove(index: Int) {
		list.removeAt(index.reversible())
	}

	/**
	 * adds [value] after the current end of the array.
	 */
	fun add(value: Any?) {
		list.add(value.purify(this))
	}

	/**
	 * inserts [value] at specified [index].
	 * all elements from that index to the end are moved one step, and none is replaced.
	 */
	fun insert(index: Int, value: Any?) {
		list.add(index.reversible(), value.purify(this))
	}

	override fun clone(): JsonArray =
		list.map { if (it is JsonContainer) it.clone() else it }.toJsonValue() as JsonArray

	internal fun uncheckedAdd(value: JsonValue) = list.add(value)

	override fun references(value: JsonContainer): Boolean = list.references(value)

	companion object: JsonCasteable {
		override val casts = JsonContainer.casts + (JsonArray::class to { value: Any -> value })
	}
}