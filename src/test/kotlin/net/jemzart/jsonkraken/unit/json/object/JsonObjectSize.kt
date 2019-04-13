package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectSize {
	@Test
	fun zero() {
		val obj = JsonObject()

		assert(obj.size == 0)
	}

	@Test
	fun one() {
		val obj = JsonObject("a" to null)

		assert(obj.size == 1)
	}

	@Test
	fun two() {
		val obj = JsonObject("a" to null, "b" to null)

		assert(obj.size == 2)
	}
}