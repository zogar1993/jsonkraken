package net.jemzart.jsonkraken.unit.json.value.string


import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.errors.primitives.CollectionOperationOnJsonPrimitiveException
import org.junit.Assert.*
import org.junit.Test

class JsonStringSet {
	@Test
	fun `by index`() {
		val primitive = JsonString("")
		runCatching { primitive[0] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}

	@Test
	fun `by key`() {
		val primitive = JsonString("")
		runCatching { primitive["a"] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}
}