package net.jemzart.jsonkraken.unit.json.value.nil


import net.jemzart.jsonkraken.JsonNull
import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.errors.primitives.CollectionOperationOnJsonPrimitiveException
import org.junit.Assert.*
import org.junit.Test

class JsonNullSet {
	@Test
	fun `by index`() {
		val primitive = JsonNull
		runCatching { primitive[0] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}

	@Test
	fun `by key`() {
		val primitive = JsonNull
		runCatching { primitive["a"] = null }.
		onSuccess { fail() }.onFailure { ex ->
			assertTrue(ex is CollectionOperationOnJsonPrimitiveException)
			ex as CollectionOperationOnJsonPrimitiveException
			assertEquals(primitive, ex.primitive)
		}
	}
}