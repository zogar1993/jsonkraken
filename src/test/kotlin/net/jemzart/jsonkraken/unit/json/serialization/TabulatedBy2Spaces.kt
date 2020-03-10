package net.jemzart.jsonkraken.unit.json.serialization


import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonTrue
import net.jemzart.jsonkraken.serializer.Tabulation
import org.junit.Assert.assertEquals
import org.junit.Test

class TabulatedBy2Spaces {
	@Test
	fun lonely() {
		assertEquals("true", JsonKraken.serialize(JsonTrue, tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `empty array`() {
		assertEquals("[\n\n]", JsonKraken.serialize(JsonArray(), tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `empty object`() {
		assertEquals("{\n\n}", JsonKraken.serialize(JsonObject(), tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `array 1 element`() {
		assertEquals("[\n  true\n]", JsonKraken.serialize(JsonArray(true), tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `array 2 elements`() {
		assertEquals("[\n  true,\n  false\n]", JsonKraken.serialize(JsonArray(true, false), tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `object 1 element`() {
		val key1 = "\"a\""
		assertEquals("{\n  $key1: true\n}", JsonKraken.serialize(JsonObject("a" to true), tabulation = Tabulation.TwoSpace))
	}

	@Test
	fun `object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assertEquals("{\n  $key1: true,\n  $key2: false\n}", JsonKraken.serialize(JsonObject("a" to true, "b" to false), tabulation = Tabulation.TwoSpace))
	}
}