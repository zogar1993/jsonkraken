package jemzart.kjson.unit

import jemzart.kjson.toJson
import jemzart.kjson.helpers.asResourceFile
import jemzart.kjson.values.JsonValue
import org.junit.Test

class JsonObjectFromJsonStringTest {
	@Test
	fun simpleString() {
		val raw = "{\"name\":\"Von Chap\"}"
		val json = raw.toJson()
		assert(json["name"].value == "Von Chap")
	}

	@Test
	fun simpleTrue() {
		val raw = "{\"name\":true}"
		val json = raw.toJson()
		assert(json["name"].value == true)
	}

	@Test
	fun simpleFalse() {
		val raw = "{\"name\":false}"
		val json = raw.toJson()
		assert(json["name"].value == false)
	}

	@Test
	fun simpleNull() {
		val raw = "{\"name\":null}"
		val json = raw.toJson()
		assert(json["name"].value == null)
	}

	@Test
	fun simpleInt() {
		val raw = "{\"name\":13}"
		val json = raw.toJson()
		assert(json["name"].value == 13)
	}

	@Test
	fun simpleNegativeInt() {
		val raw = "{\"name\":-13}"
		val json = raw.toJson()
		assert(json["name"].value == -13)
	}

	@Test
	fun simpleFloat() {
		val raw = "{\"name\":-13.22}"
		val json = raw.toJson()
		assert(json["name"].value == -13.22)
	}

	@Test
	fun simpleNegativeFloat() {
		val raw = "{\"name\":13.22}"
		val json = raw.toJson()
		assert(json["name"].value == 13.22)
	}

	@Test
	fun simpleTwoAttributesJson() {
		val raw = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}"
		val json = raw.toJson()
		assert(json["name"].value == "Von Chap")
		assert(json["warband"].value == "Sworn Brothers")
	}

	@Test
	fun subObjectJson() {
		val raw = "{\"captain\":{\"name\":\"Von Chap\"}}"
		val json = raw.toJson()
		assert((json["captain"]["name"].value == "Von Chap"))
	}

	@Test
	fun arrayWithObjects() {
		val raw = "[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 2)
	}

	@Test
	fun objectWithArrayWithObjects() {
		val raw = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}"
		val json = raw.toJson()

		val array = json["characters"].value as List<JsonValue>
		assert(array.size == 2)
	}

	@Test
	fun arrayWithInt() {
		val raw = "[2]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 1)
	}

	@Test
	fun emptyArray() {
		val raw = "[]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 0)
	}

	@Test
	fun lonelyNumber() {
		val raw = "1"
		val json = raw.toJson()
		assert(json.value == 1)
	}

	@Test
	fun backslashedQuotes() {
		val raw = "[\"\\\"\"]"
		val json = raw.toJson()
		assert(json.value.toString() == "[\"\\\"\"]")
	}

	@Test
	fun zero() {
		val raw = "0"
		val json = raw.toJson()
		assert(json.value == 0)
	}

	@Test
	fun minusZero() {
		val raw = "-0"
		val json = raw.toJson()
		assert(json.value == 0)
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