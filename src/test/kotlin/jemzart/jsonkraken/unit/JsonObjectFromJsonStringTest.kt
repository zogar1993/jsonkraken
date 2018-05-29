package jemzart.jsonkraken.unit

import jemzart.jsonkraken.*
import jemzart.jsonkraken.values.JsonNonCollection
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun simpleString() {
		val json = "{\"name\":\"Von Chap\"}".toJson()
		assert(json["name", STRING] == "Von Chap")
	}

	@Test
	fun simpleDouble() {
		val json = "{\"age\":13.22}".toJson()
		assert(json["age", DOUBLE] == 13.22)
	}

	@Test
	fun simpleNegativeDouble() {
		val json = "{\"age\":-13.22}".toJson()
		assert(json["age", DOUBLE] == -13.22)
	}

	@Test
	fun simpleTwoAttributesJson() {
		val json = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}".toJson()
		assert(json["name", STRING] == "Von Chap")
		assert(json["warband", STRING] == "Sworn Brothers")
	}

	@Test
	fun subObjectJson() {
		val json = "{\"captain\":{\"name\":\"Von Chap\"}}".toJson()
		assert((json["captain"]["name", STRING] == "Von Chap"))
	}

	@Test
	fun arrayWithObjects() {
		val json = "[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]".toJson()
		assert(json[0]["name", STRING] == "Von Chap")
		assert(json[1]["name", STRING] == "Ulf")
	}

	@Test
	fun objectWithArrayWithObjects() {
		val json = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}".toJson()
		assert(json["characters"][0]["name", STRING] == "Von Chap")
		assert(json["characters"][1]["name", STRING] == "Ulf")
	}

	@Test
	fun emptyArray() {
		val json = "[]".toJson()
		assert(json.size == 0)
	}

	@Test
	fun lonelyNumber() {
		val json = "1".toJson() as JsonNonCollection
		assert(json.value == 1)
	}

	@Test
	fun backslashedQuotes() {
		val json = "[\"\\\"\"]".toJson()
		assert(json[0, STRING] == "\\\"")
	}
}