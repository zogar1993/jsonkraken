package net.jemzart.jsonkraken.unit.json.value.nil

import net.jemzart.jsonkraken.values.JsonNull
import net.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonNullCast {
	@Test
	fun `JsonNull as nullable whatever`() {
		val value = JsonNull
		assert(value.cast<Unit?>() == null)
	}

	@Test
	fun `casting to JsonNull returns same JsonNull`() {
		val original = JsonNull
		val casted = original.cast<JsonNull>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonNull`() {
		val original = JsonNull
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonTrue`() {
		val original = JsonNull
		val casted = original.cast<Any>()

		assert(original == casted)
	}
}