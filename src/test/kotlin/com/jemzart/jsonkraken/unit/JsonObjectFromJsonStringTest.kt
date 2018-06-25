package com.jemzart.jsonkraken.unit

import com.jemzart.jsonkraken.get
import com.jemzart.jsonkraken.toJson
import com.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun simpleString() {
		val json = "{\"name\":\"Von Chap\"}".toJson()
		assert(json["name"] == "Von Chap")
	}

	@Test
	fun simpleDouble() {
		val json = "{\"age\":13.22}".toJson()
		assert(json["age"] == 13.22)
	}

	@Test
	fun simpleNegativeDouble() {
		val json = "{\"age\":-13.22}".toJson()
		assert(json["age"] == -13.22)
	}

	@Test
	fun simpleTwoAttributesJson() {
		val json = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}".toJson()
		assert(json["name"] == "Von Chap")
		assert(json["warband"] == "Sworn Brothers")
	}

	@Test
	fun subObjectJson() {
		val json = "{\"captain\":{\"name\":\"Von Chap\"}}".toJson()
		assert((json["captain"]["name"] == "Von Chap"))
	}

	@Test
	fun arrayWithObjects() {
		val json = "[{\"name\":\"Von Chap\"},{\"name\":\"Ulf\"}]".toJson()
		assert(json[0]["name"] == "Von Chap")
		assert(json[1]["name"] == "Ulf")
	}

	@Test
	fun objectWithArrayWithObjects() {
		val json = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}".toJson()
		assert(json["characters"][0]["name"] == "Von Chap")
		assert(json["characters"][1]["name"] == "Ulf")
	}

	@Test
	fun emptyArray() {
		val json = "[]".toJson() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun lonelyNumber() {
		val json = "1".toJson()
		assert(json == 1)
	}

	@Test
	fun backslashedQuotes() {
		val json = "[\"\\\"\"]".toJson()
		assert(json[0] == "\\\"")
	}
}