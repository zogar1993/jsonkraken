package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayExists {
	@Test
	fun exists() {
		val arr = JsonArray()

		arr[0] = ""

		assert(arr.exists(0))
	}

	@Test
	fun `does not exist`() {
		val arr = JsonArray()

		assert(!arr.exists(0))
	}
}