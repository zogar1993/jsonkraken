package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonNull
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonArrayContains {
	@Test
	fun `does not contain`() {
		val arr = JsonArray()
		assertFalse(arr.contains(JsonNull))
	}

	@Test
	fun `contains one`() {
		val arr = JsonArray(JsonNull)
		assertTrue(arr.contains(JsonNull))
	}

	@Test
	fun `contains two`() {
		val arr = JsonArray(JsonNull, JsonNull)
		assertTrue(arr.contains(JsonNull))
	}
}