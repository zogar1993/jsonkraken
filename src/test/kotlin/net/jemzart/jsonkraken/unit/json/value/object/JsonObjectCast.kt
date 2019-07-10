package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonValue

import org.junit.Test

class JsonObjectCast {
	@Test
	fun `casting to JsonObject returns same JsonObject`() {
		val original = JsonObject()
		val casted = original.cast<JsonObject>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonValue returns same JsonObject`() {
		val original = JsonObject()
		val casted = original.cast<JsonValue>()

		assert(original == casted)
	}

	@Test
	fun `casting to JsonContainer returns same JsonObject`() {
		val original = JsonObject()
		val casted = original.cast<JsonContainer>()

		assert(original == casted)
	}

	@Test
	fun `casting to Any returns same JsonObject`() {
		val original = JsonObject()
		val casted = original.cast<Any>()

		assert(original == casted)
	}
}