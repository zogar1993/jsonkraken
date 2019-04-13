package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.jsonSerialize
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectToStringTest {
	@Test
	fun `simple object`() {
		val obj = JsonObject()
		obj["name"] = "Von Chap"
		assert(obj.jsonSerialize() == "{\"name\":\"Von Chap\"}")
	}

	@Test
	fun `not null String?`() {
		val obj = JsonObject()
		val nullableString: String? = "Von Chap"
		obj["name"] = nullableString
		assert(obj.jsonSerialize() == "{\"name\":\"Von Chap\"}")
	}
}