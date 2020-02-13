package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import org.junit.Test

class JsonObjectKeys {
	@Test
	fun keys() {
		val obj = JsonObject("one" to 1, "two" to 2)

		val keys = obj.keys

		assert("one" in keys)
		assert("two" in keys)
		assert(keys.size == 2)
	}
}