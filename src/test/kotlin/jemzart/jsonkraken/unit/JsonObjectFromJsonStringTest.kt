package jemzart.jsonkraken.unit

import jemzart.jsonkraken.*
import jemzart.jsonkraken.helpers.asResourceFile
import jemzart.jsonkraken.values.JsonNonCollection
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun simpleString() {
		val json = "{\"name\":\"Von Chap\"}".toJson()
		assert(json["name", STRING] == "Von Chap")
	}

	@Test
	fun simpleTrue() {
		val json = "{\"captain\":true}".toJson()
		assert(json["captain", BOOLEAN])
	}

	@Test
	fun simpleFalse() {
		val json = "{\"captain\":false}".toJson()
		assert(!json["captain", BOOLEAN])
	}

	@Test
	fun simpleNull() {
		val json = "{\"name\":null}".toJson()
		assert(json["name", NULLABLE_STRING] == null)
	}

	@Test
	fun simpleInt() {
		val json = "{\"age\":42}".toJson()
		assert(json["age", INTEGER] == 42)
	}

	@Test
	fun simpleNegativeInt() {
		val json = "{\"age\":-42}".toJson()
		assert(json["age", INTEGER] == -42)
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
	fun arrayWithInt() {
		val json = "[2]".toJson()
		assert(json[0, INTEGER] == 2)
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

	@Test
	fun zero() {
		val json = "0".toJson() as JsonNonCollection
		assert(json.value == 0)
	}

	@Test
	fun minusZero() {
		val json = "-0".toJson() as JsonNonCollection
		assert(json.value == 0)
	}

	@Test(expected = Throwable::class)
	fun misspelledNull() {
		"nnnn".toJson()
	}

	@Test(expected = Throwable::class)
	fun misspelledTrue() {
		"tttt".toJson()
	}

	@Test(expected = Throwable::class)
	fun misspelledFalse() {
		"fffff".toJson()
	}

	@Test
	fun mustParse() {
		var passed = 0
		var failed = 0
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'y')
					try {
						if (text == "[1\r\n]")
							text.toJson()
						passed++
					} catch (ex: Throwable) {
						failed++
					}
			}
		}
		println("MUST: $passed parsed, $failed not parsed")
		assert(failed == 0)
	}

	@Test
	fun mustNotParse() {
		var passed = 0
		var failed = 0
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'n')
					try {
						text.toJson()
						passed++
					} catch (ex: Throwable) {
						failed++
					}
			}
		}
		println("MUST NOT: $passed parsed, $failed not parsed")
		assert(passed == 0)
	}

	@Test
	fun mayParse() {
		var passed = 0
		var failed = 0
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'i')
					try {
						text.toJson()
						passed++
					} catch (ex: Throwable) {
						failed++
					}
			}
		}
		println("MAY: $passed parsed, $failed not parsed")
	}
}