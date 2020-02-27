package net.jemzart.jsonkraken.unit.purifier

import net.jemzart.jsonkraken.*
import net.jemzart.jsonkraken.errors.transformation.InvalidJsonTypeException
import net.jemzart.jsonkraken.errors.purification.ArrayTransformationException
import net.jemzart.jsonkraken.errors.purification.InvalidKeyException
import net.jemzart.jsonkraken.errors.purification.IterableTransformationException
import net.jemzart.jsonkraken.errors.purification.MapTransformationException
import net.jemzart.jsonkraken.purifier.purify
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import org.junit.Assert.*


import org.junit.Test

class AnyPurify {
	@Test
	fun `negative zero to zero`() {
		assert(purify(-0.0) == JsonNumber(0.0))
	}

	@Test
	fun `Char to JsonString`() {
		assertEquals(JsonString("s"), purify('s'))
	}

	@Test
	fun `Int to JsonNumber`() {
		assertEquals(JsonNumber(13.0), purify(13))
	}

	@Test
	fun `null`() {
		assertEquals(JsonNull, purify(null))
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

	@Test
	fun `map with null key fails`() {
		val map = mapOf(null to null)
		kotlin.runCatching { purify(map) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is MapTransformationException)
			e as MapTransformationException
			assertEquals(map, e.map)

			assertTrue(e.inner is InvalidKeyException)
			val inner = e.inner as InvalidKeyException
			assertEquals(null, inner.value)
		}
	}

	@Test
	fun `map with invalid key fails`() {
		val map = mapOf(Unit to null)
		kotlin.runCatching { purify(map) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is MapTransformationException)
			e as MapTransformationException
			assertEquals(map, e.map)

			assertTrue(e.inner is InvalidKeyException)
			val inner = e.inner as InvalidKeyException
			assertEquals(Unit, inner.value)
		}
	}

	@Test
	fun `map with invalid value fails`() {
		val map = mapOf("" to Unit)
		kotlin.runCatching { purify(map) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is MapTransformationException)
			e as MapTransformationException
			assertEquals(map, e.map)
		}
	}

	@Test
	fun `array with invalid value fails`() {
		val array = arrayOf(Unit)
		kotlin.runCatching { purify(array) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is ArrayTransformationException)
			e as ArrayTransformationException
			assertArrayEquals(array, e.array)
		}
	}

	@Test
	fun `iterable with invalid value fails`() {
		val iterable = listOf(Unit)
		kotlin.runCatching { purify(iterable) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is IterableTransformationException)
			e as IterableTransformationException
			assertEquals(iterable, e.iterable)
		}
	}

	@Test
	fun `invalid value fails`() {
		val unit = Unit
		kotlin.runCatching { purify(unit) }.onSuccess { fail() }.onFailure { e ->
			assertTrue(e is InvalidJsonTypeException)
			e as InvalidJsonTypeException
			assertEquals(unit, e.value)
		}
	}
}