package jemzart.kjson.unit

import jemzart.kjson.values.JsonObject
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun simpleObject() {
		val obj = JsonObject()
		obj["name"] = "Von Chap"
		assert(obj.toString() == "{\"name\":\"Von Chap\"}")
	}
}