package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.purifier.MapTransformationException
import net.jemzart.jsonkraken.purifier.purify
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnyPurify {
	@Test
	fun `negative zero to zero`() {
		assert(purify(-0.0) == JsonNumber(0.0))
	}

	@Test
	fun `Char to String`() {
		assert(purify('s') == JsonString("s"))
	}

	@Test
	fun `Int to Double`() {
		assert(purify(13) == JsonNumber(13.0))
	}

	@Test
	fun `null`() {
		assert(purify(null) == JsonNull)
	}

	@Test
	fun `true`() {
		assertEquals(JsonTrue, purify(true))
	}

	@Test
	fun `false`() {
		assertEquals(JsonFalse, purify(false))
	}

	@Test
	fun `map is converted to JsonObject`() {
		val map = mapOf("A" to null)

		val obj = purify(map)

		assertTrue(obj is JsonObject)
		assertEquals(JsonNull, obj["A"])
	}

	@Test(expected = MapTransformationException::class)
	fun `map with null key fails`() {
		val map = mapOf(null to null)
		purify(map)
	}

	@Test
	fun `list is converted to JsonArray`() {
		val list = listOf(null)

		val arr = purify(list)

		assertTrue(arr is JsonArray)
		assertEquals(JsonNull, arr[0])
	}

	@Test
	fun `array is converted to JsonArray`() {
		val array = arrayOf<Any?>(null)

		val arr = purify(array)

		assertTrue(arr is JsonArray)
		assertEquals(JsonNull, arr[0])
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> purify(value) }
	}
}