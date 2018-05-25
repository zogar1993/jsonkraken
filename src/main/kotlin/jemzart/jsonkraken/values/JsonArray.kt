package jemzart.jsonkraken.values

import jemzart.jsonkraken.JSON_VALUE

class JsonArray : JsonValue() {
	override val size: Int get() = list.size
	private val list: MutableList<Any?> = mutableListOf()

	override fun iterator(): Iterator<Any?> = list.iterator()
	@Suppress("UNCHECKED_CAST")
	override fun <T> get(index: Int, shamelessHack: T): T = list[index] as T
	override fun get(index: Int): JsonValue = get(index, JSON_VALUE)

	override fun set(index: Int, value: Any?) {
		list[index] = value
	}

	fun add(item: Any?){
		list.add(item)
	}

	override fun remove(index: Int){
		list.removeAt(index)
	}

	fun insert(index: Int, value: Any?){
		list.add(index, value)
	}
}