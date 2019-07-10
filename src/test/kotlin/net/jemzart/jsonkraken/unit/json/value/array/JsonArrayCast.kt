package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonValue

import org.junit.Test

class JsonArrayCast {
	@Test
	fun `casting to JsonArray returns same JsonArray`() {
		val original = JsonArray()
		val casted = original.cast<JsonArray>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonArray`() {
		val original = JsonArray()
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonContainer returns same JsonArray`() {
		val original = JsonArray()
		val casted = original.cast<JsonContainer>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonArray`() {
		val original = JsonArray()
		val casted = original.cast<Any>()

		assert(original == casted)
	}
}