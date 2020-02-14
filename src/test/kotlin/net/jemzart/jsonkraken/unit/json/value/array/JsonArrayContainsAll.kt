package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonFalse
import net.jemzart.jsonkraken.JsonNull
import net.jemzart.jsonkraken.JsonTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonArrayContainsAll {
	@Test
	fun `does not contain`() {
		val arr = JsonArray()
		assertFalse(arr.containsAll(listOf(JsonNull)))
	}

	@Test
	fun `contains one`() {
		val arr = JsonArray(JsonTrue, JsonFalse)
		assertTrue(arr.containsAll(listOf(JsonTrue)))
	}

	@Test
	fun `contains two`() {
		val arr = JsonArray(JsonTrue, JsonFalse)
		assertTrue(arr.containsAll(listOf(JsonTrue, JsonFalse)))
	}
}