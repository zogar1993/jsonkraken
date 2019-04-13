package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun `simple String`() {
		val json = "{\"name\":\"Von Chap\"}".jsonDeserialize()
		assert(json["name"] == JsonString("Von Chap"))
	}

	@Test
	fun `simple Double`() {
		val json = "{\"age\":13.22}".jsonDeserialize()
		assert(json["age"] == JsonNumber(13.22))
	}

	@Test
	fun `simple negative Double`() {
		val json = "{\"age\":-13.22}".jsonDeserialize()
		assert(json["age"] == JsonNumber(-13.22))
	}

	@Test
	fun `simple two attributes json`() {
		val json = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}".jsonDeserialize()
		assert(json["name"] == JsonString("Von Chap"))
		assert(json["warband"] == JsonString("Sworn Brothers"))
	}

	@Test
	fun `sub object json`() {
		val json = "{\"captain\":{\"name\":\"Von Chap\"}}".jsonDeserialize()
		assert((json["captain"]["name"] == JsonString("Von Chap")))
	}

	@Test
	fun `array with objects`() {
		val json = "[{\"name\":\"Von Chap\"},{\"name\":\"Ulf\"}]".jsonDeserialize()
		assert(json[0]["name"] == JsonString("Von Chap"))
		assert(json[1]["name"] == JsonString("Ulf"))
	}

	@Test
	fun `object with array with objects`() {
		val json = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}".jsonDeserialize()
		assert(json["characters"][0]["name"] == JsonString("Von Chap"))
		assert(json["characters"][1]["name"] == JsonString("Ulf"))
	}

	@Test
	fun `empty array`() {
		val json = "[]".jsonDeserialize() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun `lonely number`() {
		val json = "1".jsonDeserialize()
		assert(json == JsonNumber(1.0))
	}

	@Test
	fun `backslashed double quotes`() {
		val json = "[\"\\\"\"]".jsonDeserialize()
		assert(json[0] == JsonString("\\\""))
	}
}