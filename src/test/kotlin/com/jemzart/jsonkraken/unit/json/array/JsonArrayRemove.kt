package com.jemzart.jsonkraken.unit.json.array

import com.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayRemove{
	@Test
	fun byInt(){
		val arr = JsonArray()
		arr.add(null)

		arr.remove(0)

		assert(arr.size == 0)
	}

	@Test
	fun byString(){
		val arr = JsonArray()
		arr.add(null)

		arr.remove("0")

		assert(arr.size == 0)
	}
}