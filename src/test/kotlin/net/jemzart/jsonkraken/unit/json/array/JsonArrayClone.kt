package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test
import net.jemzart.jsonkraken.set

class JsonArrayClone{

	@Test
	fun replication(){
		val arr1 = JsonArray(1)
		val arr2 = arr1.clone()

		assert(arr2[0] == 1)
	}

	@Test
	fun deepLiteral(){
		val arr1 = JsonArray(1)
		val arr2 = arr1.clone()

		arr2[0] = 2

		assert(arr1[0] == 1)
		assert(arr2[0] == 2)
	}

	@Test
	fun deepJsonObject(){
		val arr1 = JsonArray(JsonObject("value" to 1))
		val arr2 = arr1.clone()

		arr2[0]["value"] = 2

		assert(arr1[0]["value"] == 1)
		assert(arr2[0]["value"] == 2)
	}

	@Test
	fun deepJsonArray(){
		val arr1 = JsonArray(JsonArray(1))
		val arr2 = arr1.clone()

		arr2[0][0] = 2

		assert(arr1[0][0] == 1)
		assert(arr2[0][0] == 2)
	}
}