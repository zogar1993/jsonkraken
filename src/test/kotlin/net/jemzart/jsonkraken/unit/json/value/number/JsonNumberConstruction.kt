package net.jemzart.jsonkraken.unit.json.value.number


import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.exceptions.NoSuchPropertyException
import net.jemzart.jsonkraken.exceptions.NonCompliantNumberException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonNumberConstruction {
	@Test
	fun `invalid number construction`() {
		runCatching { JsonNumber(Double.NaN) }.
			onSuccess { Assert.fail() }.
			onFailure { e ->
				assertTrue(e is NonCompliantNumberException)
				e as NonCompliantNumberException
				assertEquals("NaN", e.value)
			}
	}
}