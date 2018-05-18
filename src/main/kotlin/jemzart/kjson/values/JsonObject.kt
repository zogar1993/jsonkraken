package jemzart.kjson.values

import java.util.*

class JsonObject internal constructor() : JsonValue {
	override fun set(index: Int, value: JsonValue) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun get(index: Int): JsonValue {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	private val map: MutableMap<String, JsonValue> = mutableMapOf()
	private val list: LinkedList<Pair<String, JsonValue>> = LinkedList()
	override val value get() = throw NoSuchElementException()
	override fun iterator(): Iterator<JsonValue> = list.map { it.second } .iterator()
	override fun get(key: String): JsonValue = map[key]!!
	override fun set(key: String, value: JsonValue) {
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

	override val jsonType get() = JsonType.Object

	fun add(key: String, value: JsonValue){
		assert(!map.containsKey(key))
		list.add(key to value)
		map[key] = value
	}

	fun insert(index: Int,  pair: Pair<String, JsonValue>){

	}

	fun insertAfer(name: String,  pair: Pair<String, JsonValue>){

	}

	fun insertBefore(name: String,  pair: Pair<String, JsonValue>){

	}
}