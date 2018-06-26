package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectRemove{
	@Test
	fun byString(){
		val obj = JsonObject()
		obj["0"] = null

		obj.remove("0")

		assert(obj.size == 0)
	}

	@Test
	fun byInt(){
		val obj = JsonObject()
		obj["0"] = null

		obj.remove(0)

		assert(obj.size == 0)
	}
}