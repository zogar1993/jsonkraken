package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectClone {
	@Test
	fun replication() {
		val original = JsonObject("0" to 1)
		val clone = original.clone()

		assert(clone["0"].cast<Int>() == 1)
	}

	@Test
	fun `deep literal`() {
		val original = JsonObject("0" to 1)
		val clone = original.clone()

		clone["0"] = 2

		assert(original["0"].cast<Int>() == 1)
		assert(clone["0"].cast<Int>() == 2)
	}

	@Test
	fun `deep JsonObject`() {
		val original = JsonObject("0" to JsonObject("value" to 1))
		val clone = original.clone()

		clone["0"]["value"] = 2

		assert(original["0"]["value"].cast<Int>() == 1)
		assert(clone["0"]["value"].cast<Int>() == 2)
	}

	@Test
	fun `deep JsonArray`() {
		val original = JsonObject("0" to JsonArray(1))
		val clone = original.clone()

		clone["0"][0] = 2

		assert(original["0"][0].cast<Int>() == 1)
		assert(clone["0"][0].cast<Int>() == 2)
	}
}