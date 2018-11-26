package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectReferences{
	@Test
	fun `no reference`(){
		val arr = JsonArray()
		val obj = JsonObject()

		assert(!obj.references(arr))
	}

	@Test
	fun `first level reference`(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		assert(obj.references(arr))
	}

	@Test
	fun `second level reference`(){
		val arr = JsonArray()
		val obj = JsonObject("0" to JsonArray(arr))

		assert(obj.references(arr))
	}
}