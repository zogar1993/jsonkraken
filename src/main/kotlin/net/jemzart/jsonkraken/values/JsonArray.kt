package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.references
import net.jemzart.jsonkraken.helpers.validateInsert
import net.jemzart.jsonkraken.toJsonArray

class JsonArray() : JsonValue {
	constructor(vararg items: Any?) : this() {
		for (item in items) {
			validateInsert(item, validateCircularReference = false)
			list.add(item)
		}
	}

	override val size: Int get() = list.size
	private val list: MutableList<Any?> = mutableListOf()

	override fun iterator(): Iterator<Any?> = list.iterator()

	override fun get(index: Int): Any? = list[if (index < 0) list.size + index else index]

	override fun set(index: Int, value: Any?) {
		validateInsert(value)
		for (i in list.size..index) list.add(null)
		list[if (index < 0) list.size + index else index] = value
	}

	override fun remove(index: Int) {
		list.removeAt(index)
	}

	override fun exists(index: Int): Boolean {
		return index < list.size
	}

	fun add(value: Any?) {
		validateInsert(value)
		list.add(value)
	}

	fun insert(index: Int, value: Any?) {
		validateInsert(value)
		list.add(index, value)
	}

	override fun clone(): JsonArray = list.map { if (it is JsonValue) it.clone() else it }.toJsonArray()

	internal fun uncheckedAdd(value: Any?) = list.add(value)

	override fun references(value: JsonValue): Boolean = list.references(value)

	override fun get(name: String): Any? = get(name.toInt())
	override fun set(name: String, value: Any?) = set(name.toInt(), value)
	override fun remove(name: String) = remove(name.toInt())
	override fun exists(name: String): Boolean = exists(name.toInt())
}