package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectKeys {
	@Test
	fun values() {
		val obj = JsonObject("one" to 1, "two" to 2)

		val values = obj.keys

		assert(values.contains("one"))
		assert(values.contains("two"))
		assert(values.size == 2)
	}
}