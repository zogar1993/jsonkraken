package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import org.junit.Test

class JsonArraySize {
	@Test
	fun zero() {
		val arr = JsonArray()

		assert(arr.size == 0)
	}

	@Test
	fun one() {
		val arr = JsonArray(null)

		assert(arr.size == 1)
	}

	@Test
	fun two() {
		val arr = JsonArray(null, null)

		assert(arr.size == 2)
	}
}