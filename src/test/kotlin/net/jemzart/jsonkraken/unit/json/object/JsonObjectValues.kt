package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectValues {
	@Test
	fun values(){
		val obj = JsonObject("one" to 1.0, "two" to 2.0)

		val values = obj.values

		assert(values.contains(1.0))
		assert(values.contains(2.0))
		assert(values.size == 2)
	}
}