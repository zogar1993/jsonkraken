package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Assert.assertEquals

import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun `simple String`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"name\":\"Von Chap\"}")
		assertEquals("Von Chap", json["name"].cast<String>())
	}

	@Test
	fun `simple Double`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"age\":13.22}")
		assertEquals(13.22, json["age"].cast(), 0.1)
	}

	@Test
	fun `simple negative Double`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"age\":-13.22}")
		assertEquals(-13.22, json["age"].cast(), 0.1)
	}

	@Test
	fun `simple two attributes json`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}")
		assertEquals("Von Chap", json["name"].cast<String>())
		assertEquals("Sworn Brothers", json["warband"].cast<String>())
	}

	@Test
	fun `sub object json`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"captain\":{\"name\":\"Von Chap\"}}")
		assertEquals("Von Chap", json["captain"]["name"].cast<String>())
	}

	@Test
	fun `array with objects`() {
		val json = JSONKraken.deserialize<JsonArray>("[{\"name\":\"Von Chap\"},{\"name\":\"Ulf\"}]")
		assertEquals("Von Chap", json[0]["name"].cast<String>())
		assertEquals("Ulf", json[1]["name"].cast<String>())
	}

	@Test
	fun `object with array with objects`() {
		val json = JSONKraken.deserialize<JsonObject>("{\"characters\":[{\"name\":\"Von Chap\"},{\"name\":\"Ulf\"}]}")
		assertEquals("Von Chap", json["characters"][0]["name"].cast<String>())
		assertEquals("Ulf", json["characters"][1]["name"].cast<String>())
	}

	@Test
	fun `empty array`() {
		val json = JSONKraken.deserialize<JsonArray>("[]")
		assertEquals(0, json.size)
	}

	@Test
	fun `lonely number`() {
		val json = JSONKraken.deserialize<JsonNumber>("1")
		assertEquals(1, json.cast<Int>())
	}

	@Test
	fun `backslashed double quotes`() {
		val json = JSONKraken.deserialize<JsonString>("\"\\\"\"")
		assertEquals("\\\"", json.cast<String>())
	}
}