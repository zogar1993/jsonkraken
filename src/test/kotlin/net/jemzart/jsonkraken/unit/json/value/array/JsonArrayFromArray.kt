package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.JsonTrue


import org.junit.Test

class JsonArrayFromArray {
	@Test
	fun `Array to JsonArray`() {
		val array = arrayOf("A", 2.0, true)

		val arr = JsonArray(*array)

		assert(arr[0] == JsonString("A"))
		assert(arr[1] == JsonNumber(2.0))
		assert(arr[2] == JsonTrue)
	}
}