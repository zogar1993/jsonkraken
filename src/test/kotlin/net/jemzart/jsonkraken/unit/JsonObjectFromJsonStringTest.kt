package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun `simple String`() {
		val json = "{\"name\":\"Von Chap\"}".toJson()
		assert(json["name"] == "Von Chap")
	}

	@Test
	fun `simple Double`() {
		val json = "{\"age\":13.22}".toJson()
		assert(json["age"] == 13.22)
	}

	@Test
	fun `simple negative Double`() {
		val json = "{\"age\":-13.22}".toJson()
		assert(json["age"] == -13.22)
	}

	@Test
	fun `simple two attributes json`() {
		val json = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}".toJson()
		assert(json["name"] == "Von Chap")
		assert(json["warband"] == "Sworn Brothers")
	}

	@Test
	fun `sub object json`() {
		val json = "{\"captain\":{\"name\":\"Von Chap\"}}".toJson()
		assert((json["captain"]["name"] == "Von Chap"))
	}

	@Test
	fun `array with objects`() {
		val json = "[{\"name\":\"Von Chap\"},{\"name\":\"Ulf\"}]".toJson()
		assert(json[0]["name"] == "Von Chap")
		assert(json[1]["name"] == "Ulf")
	}

	@Test
	fun `object with array with objects`() {
		val json = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}".toJson()
		assert(json["characters"][0]["name"] == "Von Chap")
		assert(json["characters"][1]["name"] == "Ulf")
	}

	@Test
	fun `empty array`() {
		val json = "[]".toJson() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun `lonely number`() {
		val json = "1".toJson()
		assert(json == 1.0)
	}

	@Test
	fun `backslashed double quotes`() {
		val json = "[\"\\\"\"]".toJson()
		assert(json[0] == "\\\"")
	}
}