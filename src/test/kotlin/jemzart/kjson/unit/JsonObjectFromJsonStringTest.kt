package jemzart.kjson.unit

import jemzart.kjson.*
import jemzart.kjson.helpers.asResourceFile
import jemzart.kjson.values.JsonNonCollection
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

//	@Test
//	fun arrayWithObjects() {
//		val raw = "[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]"
//		val json = raw.toJson()
//
//		val array = json as List<JsonCollection>
//		assert(array.size == 2)
//	}
//
//	@Test
//	fun objectWithArrayWithObjects() {
//		val raw = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}"
//		val json = raw.toJson()
//
//		val array = json["characters"].value as List<JsonCollection>
//		assert(array.size == 2)
//	}

//	@Test
//	fun arrayWithInt() {
//		val raw = "[2]"
//		val json = raw.toJson()
//
//		val array = json as List<JsonCollection>
//		assert(array.size == 1)
//	}
//
//	@Test
//	fun emptyArray() {
//		val raw = "[]"
//		val json = raw.toJson()
//
//		val array = json as List<JsonCollection>
//		assert(array.size == 0)
//	}

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
		println("MUST: $passed passed, $failed failed")
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
						failed++
					} catch (ex: Throwable) {
						passed++
					}
			}
		}
		println("MUST NOT: $passed passed, $failed failed")
		assert(failed == 0)
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
		println("MAY: $passed passed, $failed failed")
	}
}