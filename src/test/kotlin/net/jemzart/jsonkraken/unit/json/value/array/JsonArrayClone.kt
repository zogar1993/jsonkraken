package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.JsonObject


import org.junit.Test

class JsonArrayClone {
	@Test
	fun replication() {
		val original = JsonArray(1.0)
		val clone = original.clone()

		assert(clone[0] == JsonNumber(1.0))
	}

	@Test
	fun `deep literal`() {
		val original = JsonArray(1.0)
		val clone = original.clone()

		clone[0] = 2.0

		assert(original[0] == JsonNumber(1.0))
		assert(clone[0] == JsonNumber(2.0))
	}

	@Test
	fun `deep JsonObject`() {
		val original = JsonArray(JsonObject("value" to 1.0))
		val clone = original.clone()

		clone[0]["value"] = 2.0

		assert(original[0]["value"] == JsonNumber(1.0))
		assert(clone[0]["value"] == JsonNumber(2.0))
	}

	@Test
	fun `deep JsonArray`() {
		val original = JsonArray(JsonArray(1.0))
		val clone = original.clone()

		clone[0][0] = 2.0

		assert(original[0][0] == JsonNumber(1.0))
		assert(clone[0][0] == JsonNumber(2.0))
	}
}