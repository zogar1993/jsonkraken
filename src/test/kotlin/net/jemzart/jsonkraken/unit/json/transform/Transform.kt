package net.jemzart.jsonkraken.unit.json.transform

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.*
import org.junit.Test

class ValueTransformation {
	@Test
	fun `List to JsonArray`() {
		val list = listOf("A", 2.0, true)

		val arr = JsonKraken.transform<JsonArray>(list)

		assert(arr[0] == JsonString("A"))
		assert(arr[1] == JsonNumber(2.0))
		assert(arr[2] == JsonTrue)
	}

	@Test
	fun `Map to JsonObject`() {
		val map = mapOf("A" to 10.0, "B" to "ten")

		val arr = JsonKraken.transform<JsonObject>(map)

		assert(arr["A"] == JsonNumber(10.0))
		assert(arr["B"] == JsonString("ten"))
	}

	@Test(expected = InvalidCastException::class)
	fun `invalid cast`() {
		val cosa = JsonKraken.transform<JsonObject>(JsonArray())
		cosa
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `invalid type`() {
		JsonKraken.transform<JsonObject>(Exception())
	}
}