package net.jemzart.jsonkraken.unit.json.serialization
import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class Formatted {
	@Test
	fun `formatted lonely`() {
		assertEquals("true", JSONKraken.serialize(JsonTrue))
	}

	@Test
	fun `formatted empty array`() {
		assertEquals("[\n\n]", JSONKraken.serialize(JsonArray(), formatted = true))
	}

	@Test
	fun `formatted empty object`() {
		assertEquals("{\n\n}", JSONKraken.serialize(JsonObject(), formatted = true))
	}

	@Test
	fun `formatted array 1 element`() {
		assertEquals("[\n\ttrue\n]", JSONKraken.serialize(JsonArray(true), formatted = true))
	}

	@Test
	fun `formatted array 2 elements`() {
		assertEquals("[\n\ttrue,\n\tfalse\n]", JSONKraken.serialize(JsonArray(true, false), formatted = true))
	}

	@Test
	fun `formatted object 1 element`() {
		val key1 = "\"a\""
		assertEquals("{\n\t$key1: true\n}", JSONKraken.serialize(JsonObject("a" to true), formatted = true))
	}

	@Test
	fun `formatted object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assertEquals("{\n\t$key1: true,\n\t$key2: false\n}", JSONKraken.serialize(JsonObject("a" to true, "b" to false), formatted = true))
	}
}