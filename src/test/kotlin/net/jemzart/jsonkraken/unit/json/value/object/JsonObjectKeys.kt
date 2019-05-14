package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class JsonObjectKeys {
	@Test
	fun values() {
		val obj = JsonObject("one" to 1, "two" to 2)

		val values = obj.keys

		assert(JsonString("one") in values)
		assert(JsonString("two") in values)
		assert(values.size == 2)
	}
}