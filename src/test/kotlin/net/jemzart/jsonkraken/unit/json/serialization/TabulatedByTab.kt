package net.jemzart.jsonkraken.unit.json.serialization


import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonTrue
import net.jemzart.jsonkraken.serializer.Tabulation
import org.junit.Assert.assertEquals
import org.junit.Test

class TabulatedByTab {
	@Test
	fun lonely() {
		assertEquals("true", JsonKraken.serialize(JsonTrue, tabulation = Tabulation.Tab))
	}

	@Test
	fun `empty array`() {
		assertEquals("[\n\n]", JsonKraken.serialize(JsonArray(), tabulation = Tabulation.Tab))
	}

	@Test
	fun `empty object`() {
		assertEquals("{\n\n}", JsonKraken.serialize(JsonObject(), tabulation = Tabulation.Tab))
	}

	@Test
	fun `array 1 element`() {
		assertEquals("[\n\ttrue\n]", JsonKraken.serialize(JsonArray(true), tabulation = Tabulation.Tab))
	}

	@Test
	fun `array 2 elements`() {
		assertEquals("[\n\ttrue,\n\tfalse\n]", JsonKraken.serialize(JsonArray(true, false), tabulation = Tabulation.Tab))
	}

	@Test
	fun `object 1 element`() {
		val key1 = "\"a\""
		assertEquals("{\n\t$key1: true\n}", JsonKraken.serialize(JsonObject("a" to true), tabulation = Tabulation.Tab))
	}

	@Test
	fun `object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assertEquals("{\n\t$key1: true,\n\t$key2: false\n}", JsonKraken.serialize(JsonObject("a" to true, "b" to false), tabulation = Tabulation.Tab))
	}
}