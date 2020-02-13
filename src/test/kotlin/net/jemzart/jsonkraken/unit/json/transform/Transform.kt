package net.jemzart.jsonkraken.unit.json.transform

import net.jemzart.jsonkraken.*
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.exceptions.UnexpectedJsonValueException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

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

	@Test
	fun `unexpected destination json value`() {
		runCatching { JsonKraken.transform<JsonObject>(JsonArray()) }.
			onSuccess { Assert.fail() }.
			onFailure { e ->
				assertTrue(e is UnexpectedJsonValueException)
				e as UnexpectedJsonValueException
				assertEquals(JsonObject::class, e.expected)
				assertEquals(JsonArray::class, e.actual)
			}
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `invalid type value`() {
		JsonKraken.transform<JsonObject>(Exception())
	}
}