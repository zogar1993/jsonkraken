package jemzart.kjson.unit

import jemzart.kjson.jsonObject
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun simpleObject() {
		val jsonObject = jsonObject()
		jsonObject["name"] = "Von Chap"
		assert(jsonObject.toString() == "{\"name\":\"Von Chap\"}")
	}
}