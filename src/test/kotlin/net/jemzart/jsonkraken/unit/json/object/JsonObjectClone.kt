package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectClone {
	@Test
	fun replication() {
		val arr1 = JsonObject("0" to 1.0)
		val arr2 = arr1.clone()

		assert(arr2["0"] == JsonNumber(1.0))
	}

	@Test
	fun `deep literal`() {
		val arr1 = JsonObject("0" to 1.0)
		val arr2 = arr1.clone()

		arr2["0"] = 2.0

		assert(arr1["0"] == JsonNumber(1.0))
		assert(arr2["0"] == JsonNumber(2.0))
	}

	@Test
	fun `deep JsonObject`() {
		val arr1 = JsonObject("0" to JsonObject("value" to 1))
		val arr2 = arr1.clone()

		arr2["0"]["value"] = 2.0

		assert(arr1["0"]["value"] == JsonNumber(1.0))
		assert(arr2["0"]["value"] == JsonNumber(2.0))
	}

	@Test
	fun `deep JsonArray`() {
		val arr1 = JsonObject("0" to JsonArray(1.0))
		val arr2 = arr1.clone()

		arr2["0"][0] = 2

		assert(arr1["0"][0] == JsonNumber(1.0))
		assert(arr2["0"][0] == JsonNumber(2.0))
	}
}