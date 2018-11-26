package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayFromArray{
	@Test
	fun `Array to JsonArray`(){
		val array = arrayOf("A", 2.0, true)

		val arr = JsonArray(*array)

		assert(arr[0] == "A")
		assert(arr[1] == 2.0)
		assert(arr[2] == true)
	}
}