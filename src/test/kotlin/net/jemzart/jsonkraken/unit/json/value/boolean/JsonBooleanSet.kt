package net.jemzart.jsonkraken.unit.json.value.boolean


import net.jemzart.jsonkraken.*
import net.jemzart.jsonkraken.errors.primitives.CollectionOperationOnJsonPrimitiveException
import org.junit.Assert.*
import org.junit.Test

class JsonBooleanSet {
	@Test
	fun `true by index`() {
		val primitive = JsonTrue
		runCatching { primitive[0] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}

	@Test
	fun `true by key`() {
		val primitive = JsonTrue
		runCatching { primitive["a"] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}

	@Test
	fun `false by index`() {
		val primitive = JsonFalse
		runCatching { primitive[0] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}

	@Test
	fun `false by key`() {
		val primitive = JsonFalse
		runCatching { primitive["a"] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}
}