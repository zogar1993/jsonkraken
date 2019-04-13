package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Test

class DoubleNormalize {
	@Test
	fun `zero stays zero`() {
		assert(JsonNumber(0.0).value == 0.0)
	}

	@Test
	fun `minus zero changes to zero`() {
		assert(JsonNumber(-0.0).value == 0.0)
	}

	@Test
	fun `one stays one`() {
		assert(JsonNumber(1.0).value == 1.0)
	}

	@Test
	fun `minus one stays minus one`() {
		assert(JsonNumber(-1.0).value == -1.0)
	}
}