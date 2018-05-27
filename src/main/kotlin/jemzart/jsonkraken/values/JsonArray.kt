package jemzart.jsonkraken.values

import jemzart.jsonkraken.JSON_VALUE

class JsonArray : JsonValue() {
	override val size: Int get() = list.size
	private val list: MutableList<Any?> = mutableListOf()

	override fun iterator(): Iterator<Any?> = list.iterator()

	@Suppress("UNCHECKED_CAST")
	override fun <T> get(index: Int, shamelessHack: T): T = list[if (index < 0) list.size + index else index] as T
	override fun get(index: Int): JsonValue = get(index, JSON_VALUE)
	override fun set(index: Int, value: Any?) {
		for (i in list.size..index)	list.add(null)
		list[if (index < 0) list.size + index else index] = value
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

	override fun <T> get(name: String, shamelessHack: T): T = get(name.toInt(), shamelessHack)
	override fun get(name: String): JsonValue = get(name.toInt())
	override fun set(name: String, value: Any?) = set(name.toInt(), value)
	override fun remove(name: String) = remove(name.toInt())
}