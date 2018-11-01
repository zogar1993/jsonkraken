package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
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