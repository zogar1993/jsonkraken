package net.jemzart.jsonkraken.unit.json.value.nil

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.values.JsonNull
import net.jemzart.jsonkraken.values.JsonValue

import org.junit.Assert.assertEquals
import org.junit.Test

class JsonNullCast {
	@Test
	fun `JsonNull as nullable whatever`() {
		val value = JsonNull
		assert(value.cast<Unit?>() == null)
	}

	@Test(expected = InvalidCastException::class)
	fun `casting to Any returns null`() {
		val json = JsonNull
		json.cast<Any>()
	}

	@Test
	fun `casting to Any? returns null`() {
		val json = JsonNull
		val result = json.cast<Any?>()

		assert(null == result)
	}

	@Test
	fun `cast to non nullable whatever throws an InvalidCastException`() {
		try {
			JsonNull.cast<Unit>()
			assert(false)
		} catch (ex: InvalidCastException) {
			assertEquals(JsonNull::class, ex.from)
			assertEquals(Unit::class, ex.to)
		}
	}
}