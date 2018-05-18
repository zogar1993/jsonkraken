package jemzart.kjson.values

import java.util.*

class JsonObject internal constructor() : JsonCollection {
	private val map: MutableMap<String, Any?> = mutableMapOf()
	private val list: LinkedList<Pair<String, Any?>> = LinkedList()

	override fun get(index: Int): JsonCollection = throw UnsupportedOperationException()
	override fun set(index: Int, value: JsonCollection) = throw UnsupportedOperationException()
	override fun <T> get(index: Int, shamelessHack: T): T = throw UnsupportedOperationException()
	override fun set(index: Int, value: Any?) = throw UnsupportedOperationException()

	override fun iterator(): Iterator<Any?> = list.map { it.second } .iterator()

	override fun <T> get(key: String, shamelessHack: T): T = map[key]!! as T
	override fun get(key: String): JsonCollection = get(key, JsonCollection.dummy)

	override fun set(key: String, value: JsonCollection) = set(key, value as Any)
	override fun set(key: String, value: Any?){
		if(map.containsKey(key)) {
			val obj = map[key]
			list.removeIf { it.second == obj }
		}
		list.add(key to value)
		map[key] = value
	}

	override fun toString(): String {
		val stringBuilder = StringBuilder()
		stringBuilder.append("{")
		var first = true
		for (pair in list) {
			if (!first) stringBuilder.append(",") else first = false
			stringBuilder.append("\"${pair.first}\":${pair.second}")
		}
		stringBuilder.append("}")
		return stringBuilder.toString()
	}

	fun add(key: String, value: Any){
		assert(!map.containsKey(key))
		list.add(key to value)
		map[key] = value
	}

	fun insert(index: Int,  pair: Pair<String, JsonCollection>){

	}

	fun insertAfer(name: String,  pair: Pair<String, JsonCollection>){

	}

	fun insertBefore(name: String,  pair: Pair<String, JsonCollection>){

	}
}