package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.JsonObject
import org.junit.Test

class JsonObjectValues {
	@Test
	fun values() {
		val obj = JsonObject("one" to 1.0, "two" to 2.0)

		val values = obj.values

		assert(JsonNumber(1.0) in values)
		assert(JsonNumber(2.0) in values)
		assert(values.size == 2)
	}
}