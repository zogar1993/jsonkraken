package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializationException
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.JsonObject

import org.junit.Assert.assertEquals
import org.junit.Test

class ObjectDeserialization {
	@Test
	fun empty() {
		val json = JsonKraken.deserialize<JsonObject>("{}")
		assertEquals(0, json.size)
	}

	@Test
	fun `one element`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"a\":\"b\"}")
		assertEquals("b", json["a"].cast())
	}

	@Test
	fun `two elements`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"a\":\"b\",\"c\":\"d\"}")
		assertEquals("b", json["a"].cast())
		assertEquals("d", json["c"].cast())
	}

	@Test
	fun `duplicate key and value leaves just one of them`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"a\":\"b\",\"a\":\"b\"}")
		assertEquals("b", json["a"].cast())
		assertEquals(1, json.size)
	}

	@Test
	fun `duplicate key leaves last`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"a\":\"b\",\"a\":\"c\"}")
		assertEquals("c", json["a"].cast())
		assertEquals(1, json.size)
	}

	@Test
	fun `empty key`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"\":0}")
		assertEquals(0, json[""].cast<Int>())
	}

	@Test
	fun `escaped null in key`() {
		val json = JsonKraken.deserialize<JsonObject>("{\"foo\\u0000bar\":42}")
		assertEquals(42, json["foo\\u0000bar"].cast<Int>())
	}

	@Test
	fun `allowed white spaces`() {
		val json = JsonKraken.deserialize<JsonObject>("$WS{$WS${str("0")}$WS:$WS${str("A")}$WS,$WS${str("1")}$WS:$WS${str("B")}$WS}$WS")
		assertEquals("A", json["0"].cast<String>())
		assertEquals("B", json["1"].cast<String>())
	}

	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonObject>("{")
	}
}