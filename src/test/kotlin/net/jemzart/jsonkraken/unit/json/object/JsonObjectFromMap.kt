package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.toJsonObject
import org.junit.Test

class JsonObjectFromMap{
	@Test
	fun listToJsonArray(){
		val map = mapOf("A" to 10.0, "B" to "ten")

		val arr = map.toJsonObject()

		assert(arr["A"] == 10.0)
		assert(arr["B"] == "ten")
	}
}