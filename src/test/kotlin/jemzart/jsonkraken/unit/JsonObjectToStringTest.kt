package jemzart.jsonkraken.unit

import jemzart.jsonkraken.toJsonString
import jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun simpleObject() {
		val obj = JsonObject()
		obj["name"] = "Von Chap"
		assert(obj.toJsonString() == "{\"name\":\"Von Chap\"}")
	}

	@Test
	fun notNullNullableString() {
		val obj = JsonObject()
		val nullableString: String? = "Von Chap"
		obj["name"] = nullableString
		assert(obj.toJsonString() == "{\"name\":\"Von Chap\"}")
	}
}