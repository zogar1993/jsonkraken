package net.jemzart.jsonkraken.unit.json.number

import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonNumberCast {
	@Test
	fun `zero stays zero`() {
		val value = JsonNumber(0.0)
		assert(value.cast<Double>() == 0.0)
	}

	@Test
	fun `minus zero changes to zero`() {
		val value = JsonNumber(-0.0)
		assert(value.cast<Double>() == 0.0)
	}

	@Test
	fun `one stays one`() {
		val value = JsonNumber(1.0)
		assert(value.cast<Double>() == 1.0)
	}

	@Test
	fun `minus one stays minus one`() {
		val value = JsonNumber(-1.0)
		assert(value.cast<Double>() == -1.0)
	}

	@Test
	fun `casting to JsonNumber returns same JsonNumber`() {
		val original = JsonNumber(1)
		val casted = original.cast<JsonNumber>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonNumber`() {
		val original = JsonNumber(1)
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonNumber`() {
		val original = JsonNumber(1)
		val casted = original.cast<Any>()

		assert(original == casted)
	}
}