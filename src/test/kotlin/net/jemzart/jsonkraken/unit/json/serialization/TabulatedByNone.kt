package net.jemzart.jsonkraken.unit.json.serialization


import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonTrue
import net.jemzart.jsonkraken.serializer.Tabulation
import org.junit.Assert.assertEquals
import org.junit.Test

class TabulatedByNone {
	@Test
	fun lonely() {
		assertEquals("true", JsonKraken.serialize(JsonTrue, tabulation = Tabulation.None))
	}

	@Test
	fun `empty array`() {
		assertEquals("[]", JsonKraken.serialize(JsonArray(), tabulation = Tabulation.None))
	}

	@Test
	fun `empty object`() {
		assertEquals("{}", JsonKraken.serialize(JsonObject(), tabulation = Tabulation.None))
	}

	@Test
	fun `array 1 element`() {
		assertEquals("[true]", JsonKraken.serialize(JsonArray(true), tabulation = Tabulation.None))
	}

	@Test
	fun `array 2 elements`() {
		assertEquals("[true,false]", JsonKraken.serialize(JsonArray(true, false), tabulation = Tabulation.None))
	}

	@Test
	fun `object 1 element`() {
		val key1 = "\"a\""
		assertEquals("{$key1:true}", JsonKraken.serialize(JsonObject("a" to true), tabulation = Tabulation.None))
	}

	@Test
	fun `object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assertEquals("{$key1:true,$key2:false}", JsonKraken.serialize(JsonObject("a" to true, "b" to false), tabulation = Tabulation.None))
	}
}