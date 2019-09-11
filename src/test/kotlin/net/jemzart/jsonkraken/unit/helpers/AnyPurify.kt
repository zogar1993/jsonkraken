package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnyPurify {
	@Test
	fun `negative zero to zero`() {
		assert((-0.0).purify() == JsonNumber(0.0))
	}

	@Test
	fun `Char to String`() {
		assert('s'.purify() == JsonString("s"))
	}

	@Test
	fun `Int to Double`() {
		assert(13.purify() == JsonNumber(13.0))
	}

	@Test
	fun `null`() {
		assert(null.purify() == JsonNull)
	}

	@Test
	fun `true`() {
		assertEquals(JsonTrue, true.purify())
	}

	@Test
	fun `false`() {
		assertEquals(JsonFalse, false.purify())
	}

	@Test
	fun `map is converted to JsonObject`() {
		val map = mapOf("A" to null)

		val obj = map.purify()

		assertTrue(obj is JsonObject)
		assertEquals(JsonNull, obj["A"])
	}

	@Test(expected = NullPointerException::class)
	fun `map with null key fails`() {
		val map = mapOf(null to null)
		map.purify()
	}

	@Test
	fun `list is converted to JsonArray`() {
		val list = listOf(null)

		val arr = list.purify()

		assertTrue(arr is JsonArray)
		assertEquals(JsonNull, arr[0])
	}

	@Test
	fun `array is converted to JsonArray`() {
		val array = arrayOf<Any?>(null)

		val arr = array.purify()

		assertTrue(arr is JsonArray)
		assertEquals(JsonNull, arr[0])
	}

	@Test
	fun `self circularity`() {
		val arr = JsonArray()
		runCatching { arr.purify(arr) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `A to B to A circularity`() {
		val obj = JsonObject()
		val arr = JsonArray(obj)
		runCatching { arr.purify(obj) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == obj)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> value.purify() }
	}
}