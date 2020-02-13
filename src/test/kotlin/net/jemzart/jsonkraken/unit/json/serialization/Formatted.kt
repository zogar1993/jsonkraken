package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonTrue


import org.junit.Assert.assertEquals
import org.junit.Test

class Formatted {
	@Test
	fun `formatted lonely`() {
		assertEquals("true", JsonKraken.serialize(JsonTrue))
	}

	@Test
	fun `formatted empty array`() {
		assertEquals("[\n\n]", JsonKraken.serialize(JsonArray(), formatted = true))
	}

	@Test
	fun `formatted empty object`() {
		assertEquals("{\n\n}", JsonKraken.serialize(JsonObject(), formatted = true))
	}

	@Test
	fun `formatted array 1 element`() {
		assertEquals("[\n\ttrue\n]", JsonKraken.serialize(JsonArray(true), formatted = true))
	}

	@Test
	fun `formatted array 2 elements`() {
		assertEquals("[\n\ttrue,\n\tfalse\n]", JsonKraken.serialize(JsonArray(true, false), formatted = true))
	}

	@Test
	fun `formatted object 1 element`() {
		val key1 = "\"a\""
		assertEquals("{\n\t$key1: true\n}", JsonKraken.serialize(JsonObject("a" to true), formatted = true))
	}

	@Test
	fun `formatted object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assertEquals("{\n\t$key1: true,\n\t$key2: false\n}", JsonKraken.serialize(JsonObject("a" to true, "b" to false), formatted = true))
	}
}