package jemzart.jsonkraken.unit.json.`object`

import jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectRemove{
	@Test
	fun byString(){
		val arr = JsonObject()
		arr["0"] = null

		arr.remove("0")

		assert(arr.size == 0)
	}

	@Test
	fun byInt(){
		val arr = JsonObject()
		arr["0"] = null

		arr.remove(0)

		assert(arr.size == 0)
	}
}