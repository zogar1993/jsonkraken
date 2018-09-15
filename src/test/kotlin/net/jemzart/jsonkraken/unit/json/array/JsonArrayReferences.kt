package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArrayReferences{

	@Test
	fun noReference(){
		val obj = JsonObject()
		val arr = JsonArray()

		assert(!arr.references(obj))
	}

	@Test
	fun firstLevelReference(){
		val obj = JsonObject()
		val arr = JsonArray(obj)

		assert(arr.references(obj))
	}

	@Test
	fun secondLevelReference(){
		val obj = JsonObject()
		val arr = JsonArray(JsonArray(obj))

		assert(arr.references(obj))
	}
}