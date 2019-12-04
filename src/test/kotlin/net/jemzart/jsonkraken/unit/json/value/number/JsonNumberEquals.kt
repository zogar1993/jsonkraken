package net.jemzart.jsonkraken.unit.json.value.number


import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Test

class JsonNumberEquals {
	@Test
	fun `a JsonNumber equals another JsonNumber of the same value`() {
		val a = JsonNumber(1.0)
		val b = JsonNumber(1.0)
		assert(a == b)
	}
}