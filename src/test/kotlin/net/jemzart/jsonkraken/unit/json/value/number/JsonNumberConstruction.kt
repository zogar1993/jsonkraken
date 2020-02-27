package net.jemzart.jsonkraken.unit.json.value.number


import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.errors.primitives.NonCompliantNumberException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonNumberConstruction {
	@Test
	fun `Double NaN fails`() {
		runCatching { JsonNumber(Double.NaN) }.
			onSuccess { Assert.fail() }.
			onFailure { e ->
				assertTrue(e is NonCompliantNumberException)
				e as NonCompliantNumberException
				assertEquals("NaN", e.value)
			}
	}

	@Test
	fun `Double negative infinity fails`() {
		runCatching { JsonNumber(Double.NEGATIVE_INFINITY) }.
			onSuccess { Assert.fail() }.
			onFailure { e ->
				assertTrue(e is NonCompliantNumberException)
				e as NonCompliantNumberException
				assertEquals("-Infinity", e.value)
			}
	}

	@Test
	fun `Double infinity fails`() {
		runCatching { JsonNumber(Double.POSITIVE_INFINITY) }.
			onSuccess { Assert.fail() }.
			onFailure { e ->
				assertTrue(e is NonCompliantNumberException)
				e as NonCompliantNumberException
				assertEquals("Infinity", e.value)
			}
	}

	@Test
	fun `Double -0 turns into 0`() {
		val number = JsonNumber(-0.0)
		assertEquals("0", number.value)
	}
}