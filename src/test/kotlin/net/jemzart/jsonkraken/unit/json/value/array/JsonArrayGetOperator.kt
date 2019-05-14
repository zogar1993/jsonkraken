package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayGetOperator {
	private val insertion = JsonArray()

	@Test
	fun `by Int`() {
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr[0] == insertion)
	}

	@Test
	fun `by String`() {
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr["0"] == insertion)
	}

	@Test
	fun `reverse notation`() {
		val arr = JsonArray()
		arr.add(null)
		arr.add(insertion)

		assert(arr[-1] == insertion)
	}
}