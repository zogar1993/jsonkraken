package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray

import org.junit.Test

class JsonArrayRemove {
	@Test
	fun simple() {
		val arr = JsonArray()
		arr.add(null)

		arr.remove(0)

		assert(arr.size == 0)
	}

	@Test
	fun `reverse notation`() {
		val arr = JsonArray()
		arr.add(1)
		arr.add(2)
		arr.add(3)

		arr.remove(-2)

		assert(arr[0].cast<Int>() == 1)
		assert(arr[1].cast<Int>() == 3)
	}
}