package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonArrayIsEmpty {
	@Test
	fun `is empty`() {
		val arr = JsonArray()
		assertTrue(arr.isEmpty())
	}

	@Test
	fun `is not empty`() {
		val arr = JsonArray(1)
		assertTrue(arr.isNotEmpty())
	}
}