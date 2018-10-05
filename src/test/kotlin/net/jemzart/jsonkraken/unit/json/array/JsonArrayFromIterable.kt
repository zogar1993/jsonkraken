package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.toJsonArray
import org.junit.Test

class JsonArrayFromIterable{
	@Test
	fun listToJsonArray(){
		val list = listOf("A", 2.0, true)

		val arr = list.toJsonArray()

		assert(arr[0] == "A")
		assert(arr[1] == 2.0)
		assert(arr[2] == true)
	}
}