package jemzart.kjson.unit

import jemzart.kjson.emptyJsonObject
import jemzart.kjson.values.JsonString
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun simpleObject() {
		val jsonObject = emptyJsonObject()
		jsonObject["name"] = JsonString("Von Chap")
		assert(jsonObject.toString() == "{\"name\":\"Von Chap\"}")
	}
}