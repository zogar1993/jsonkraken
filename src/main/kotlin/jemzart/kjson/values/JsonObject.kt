package jemzart.kjson.values

import jemzart.kjson.JSON_VALUE
import jemzart.kjson.enums.JsonObjectPosition
import jemzart.kjson.operations.JsonObjectInsertion
import java.util.*

class JsonObject : JsonValue() {
	override val size: Int get() = list.size
	private val map: MutableMap<String, Any?> = mutableMapOf()
	private val list: LinkedList<Pair<String, Any?>> = LinkedList()

	override fun iterator(): Iterator<Pair<String, Any?>> = list.iterator()

	override fun <T> get(index: Int, shamelessHack: T): T = list[index] as T
	override fun <T> get(key: String, shamelessHack: T): T = map[key] as T
	override fun get(key: String): JsonValue = get(key, JSON_VALUE)
	override fun set(key: String, value: Any?){
		if(map.containsKey(key)) {
			val obj = map[key]
			list.removeIf { it.second == obj }
		}
		list.add(key to value)
		map[key] = value
	}

	fun add(key: String, value: Any){
		assert(!map.containsKey(key))
		list.add(key to value)
		map[key] = value
	}

	fun insert(insertion: JsonObjectInsertion<String, Any?>){
		assert(map.containsKey(insertion.relativeTo))
		assert(!map.containsKey(insertion.pair.first))
		map[insertion.pair.first] = insertion.pair.second
		for(i in 0 until size)
			if(insertion.relativeTo == list[i].first){
				val index = if (insertion.position == JsonObjectPosition.Before) i else i + 1
				list.add(index, insertion.pair)
				return
			}
	}

	fun delete(key: String){
		if(map.containsKey(key)){
			map.remove(key)
			list.removeIf { it.first == key }
		}
	}
}