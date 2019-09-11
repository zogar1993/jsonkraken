package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.values.*
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class ArrayDeserialization {
	@Test
	fun empty() {
		val json = JSONKraken.deserialize<JsonArray>("[]")
		assertEquals(0, json.size)
	}

	@Test
	fun emptyString() {
		val json = JSONKraken.deserialize<JsonArray>("[\"\"]")
		assertEquals("", json[0].cast<String>())
	}

	@Test
	fun heterogeneous() {
		val json = JSONKraken.deserialize<JsonArray>("[null,1,\"1\",{},[],true,false]")
		assertEquals(JsonNull, json[0])
		assertEquals(JsonNumber(1.0), json[1])
		assertEquals(JsonString("1"), json[2])
		assertTrue(json[3] is JsonObject)
		assertTrue(json[4] is JsonArray)
		assertEquals(JsonTrue, json[5])
		assertEquals(JsonFalse, json[6])
	}

	@Test
	fun `allowed white spaces`() {
		val json = JSONKraken.deserialize<JsonArray>("$WS[$WS${str("A")}$WS,$WS${str("B")}$WS]$WS")
		assertEquals("A", json[0].cast())
		assertEquals("B", json[1].cast())
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonValue>("[.")
	}
}