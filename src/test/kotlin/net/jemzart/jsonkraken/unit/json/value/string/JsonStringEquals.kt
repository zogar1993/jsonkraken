package net.jemzart.jsonkraken.unit.json.value.string

import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class JsonStringEquals {
	@Test
	fun `a JsonString equals another JsonString of the same value`() {
		val a = JsonString("kraken")
		val b = JsonString("kraken")
		assert(a == b)
	}
}