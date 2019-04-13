package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Test

class JsonArrayRemove {
	@Test
	fun `by Int`() {
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

		assert(arr[0] == JsonNumber(1.0))
		assert(arr[1] == JsonNumber(3.0))
	}
}