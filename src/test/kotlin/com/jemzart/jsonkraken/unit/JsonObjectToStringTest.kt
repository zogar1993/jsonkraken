package com.jemzart.jsonkraken.unit

import com.jemzart.jsonkraken.toJsonString
import com.jemzart.jsonkraken.values.JsonObject
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