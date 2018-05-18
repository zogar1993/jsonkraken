package jemzart.kjson.unit

import jemzart.kjson.jsonArray
import jemzart.kjson.jsonObject
import jemzart.kjson.values.JsonString
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun simpleObject() {
		val jsonObject = jsonObject()
		jsonObject["name"] = JsonString("Von Chap")
		assert(jsonObject.toString() == "{\"name\":\"Von Chap\"}")
	}
}