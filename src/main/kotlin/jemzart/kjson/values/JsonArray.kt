package jemzart.kjson.values

import jemzart.kjson.JSON_VALUE

class JsonArray : JsonValue() {
	private val list: MutableList<Any?> = mutableListOf()

	override fun iterator(): Iterator<Any?> = list.iterator()

	override fun <T> get(index: Int, shamelessHack: T): T = list[index] as T
	override fun get(index: Int): JsonValue = get(index, JSON_VALUE)

	override fun set(index: Int, value: Any?) {
		list[index] = value
	}

	override fun toString(): String {
		val stringBuilder = StringBuilder()
		stringBuilder.append("[")
		var first = true
		for (item in list) {
			if (!first) stringBuilder.append(",") else first = false
			stringBuilder.append(item.toString())
		}
		stringBuilder.append("]")
		return stringBuilder.toString()
	}

	fun add(item: Any?){
		list.add(item)
	}
}