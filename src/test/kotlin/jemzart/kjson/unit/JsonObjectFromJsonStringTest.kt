package jemzart.kjson.unit

import jemzart.kjson.KJson.Companion.toJson
import jemzart.kjson.values.JsonValue
import org.junit.Test
import java.io.File

class JsonObjectFromJsonStringTest {
	@Test
	fun simpleString(){
		val raw = "{\"name\":\"Von Chap\"}"
		val json = raw.toJson()
		assert(json["name"].value == "Von Chap")
	}

	@Test
	fun simpleTrue(){
		val raw = "{\"name\":true}"
		val json = raw.toJson()
		assert(json["name"].value == true)
	}

	@Test
	fun simpleFalse(){
		val raw = "{\"name\":false}"
		val json = raw.toJson()
		assert(json["name"].value == false)
	}

	@Test
	fun simpleNull(){
		val raw = "{\"name\":null}"
		val json = raw.toJson()
		assert(json["name"].value == null)
	}

	@Test
	fun simpleInt(){
		val raw = "{\"name\":13}"
		val json = raw.toJson()
		assert(json["name"].value == 13)
	}

	@Test
	fun simpleNegativeInt(){
		val raw = "{\"name\":-13}"
		val json = raw.toJson()
		assert(json["name"].value == -13)
	}

	@Test
	fun simpleFloat(){
		val raw = "{\"name\":-13.22}"
		val json = raw.toJson()
		assert(json["name"].value == -13.22)
	}

	@Test
	fun simpleNegativeFloat(){
		val raw = "{\"name\":13.22}"
		val json = raw.toJson()
		assert(json["name"].value == 13.22)
	}

	@Test
	fun simpleTwoAttributesJson(){
		val raw = "{\"name\":\"Von Chap\",\"warband\":\"Sworn Brothers\"}"
		val json = raw.toJson()
		assert(json["name"].value == "Von Chap")
		assert(json["warband"].value == "Sworn Brothers")
	}

	@Test
	fun subObjectJson(){
		val raw = "{\"captain\":{\"name\":\"Von Chap\"}}"
		val json = raw.toJson()
		assert((json["captain"]["name"].value == "Von Chap"))
	}

	@Test
	fun arrayWithObjects(){
		val raw = "[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 2)
	}

	@Test
	fun objectWithArrayWithObjects(){
		val raw = "{\"characters\":[{\"name\":\"Von Chap\"}, {\"name\":\"Ulf\"}]}"
		val json = raw.toJson()

		val array = json["characters"].value as List<JsonValue>
		assert(array.size == 2)
	}

	@Test
	fun arrayWithInt(){
		val raw = "[2]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 1)
	}

	@Test
	fun emptyArray(){
		val raw = "[]"
		val json = raw.toJson()

		val array = json.value as List<JsonValue>
		assert(array.size == 0)
	}

	@Test
	fun lonelyNumber(){
		val raw = "1"
		val json = raw.toJson()
		assert(json.value == 1)
	}

	@Test
	fun backslashedQuotes(){
		val raw = "[\"\\\"\"]"
		val json = raw.toJson()
		var result = "\"\\\"\""
		result = "[$result]"
		assert(json.value.toString() == result)
	}

	@Test
	fun zero(){
		val raw = "0"
		val json = raw.toJson()
		assert(json.value == 0)
	}

	@Test
	fun validationFiles(){
		var passed = 0
		var failed = 0

		File("C:\\Proyectos\\von-chap\\json tests\\test_parsing").walk().forEach {
			if (!it.isDirectory){

				val text =  it.readText()
//				println("${it.name} $text")
				when (it.name[0]) {
					'n' -> try {
						val json = text.toJson()
						println("${it.name} ----- $text ----- $json")
						failed++
					} catch (ex: Throwable) {
						passed++
					}
					'y' -> try {
						val json = text.toJson()
						passed++
					} catch (ex: Throwable) {
						println("${it.name} ----- $text")
						failed++
					}
//					'i' -> try {
//						val json = text.toJson()
//						passed++
//					} catch (ex: Throwable) {
//						println("${it.name} ----- $text")
//						failed++
//					}
				}
			}
		}
		println("passed: $passed, failed: $failed")
	}
}