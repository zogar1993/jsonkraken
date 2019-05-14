package net.jemzart.jsonkraken.unit.json.value.boolean

import net.jemzart.jsonkraken.values.JsonBoolean
import net.jemzart.jsonkraken.values.JsonFalse
import net.jemzart.jsonkraken.values.JsonTrue
import net.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonBooleanCast {
	@Test
	fun `JsonTrue as boolean`() {
		val value = JsonTrue
		assert(value.cast<Boolean>() == true)
	}

	@Test
	fun `JsonFalse as boolean`() {
		val value = JsonFalse
		assert(value.cast<Boolean>() == false)
	}

	@Test
	fun `casting to JsonTrue returns same JsonTrue`() {
		val original = JsonTrue
		val casted = original.cast<JsonTrue>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonBoolean returns same JsonTrue`() {
		val original = JsonTrue
		val casted = original.cast<JsonBoolean>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonTrue`() {
		val original = JsonTrue
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonTrue`() {
		val original = JsonTrue
		val casted = original.cast<Any>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonFalse returns same JsonFalse`() {
		val original = JsonFalse
		val casted = original.cast<JsonFalse>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonBoolean returns same JsonFalse`() {
		val original = JsonFalse
		val casted = original.cast<JsonBoolean>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonFalse`() {
		val original = JsonFalse
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonFalse`() {
		val original = JsonFalse
		val casted = original.cast<Any>()

		assert(original == casted)
	}
}