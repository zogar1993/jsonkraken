package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.copy
import net.jemzart.jsonkraken.purifier.purify

/**
 * @constructor empty json array.
 */
class JsonArray() : JsonContainer(), Iterable<JsonValue> {
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
	internal val list: MutableList<JsonValue> = mutableListOf()

	/**
	 * @return an iterator over all its items.
	 */
	override fun iterator(): Iterator<JsonValue> = list.iterator()

	override fun get(index: Int): JsonValue = list[index.reversible()]

	override fun set(index: Int, value: Any?) {
		val purified = value.purify()
		throwIfHasAReferenceOnMe(purified)
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
		val purified = value.purify()
		throwIfHasAReferenceOnMe(purified)
		list.add(purified)
	}

	/**
	 * inserts [value] at specified [index].
	 * all elements from that index to the end are moved one step, and none is replaced.
	 */
	fun insert(index: Int, value: Any?) {
		val purified = value.purify()
		throwIfHasAReferenceOnMe(purified)
		list.add(index.reversible(), purified)
	}

	override fun clone() = JsonArray(*list.map { copy(it) }.toTypedArray())

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(list)
}