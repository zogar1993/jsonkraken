package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.exceptions.NoSuchIndexException
import net.jemzart.jsonkraken.JsonArray
import org.junit.Assert.*
import org.junit.Test

class JsonArrayGetOperator {
	private val insertion = JsonArray()

	@Test
	fun `by Int`() {
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr[0] == insertion)
	}

	@Test
	fun `by String`() {
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr["0"] == insertion)
	}

	@Test
	fun `reverse notation`() {
		val arr = JsonArray()
		arr.add(null)
		arr.add(insertion)

		assert(arr[-1] == insertion)
	}

	@Test
	fun `getting a non existent index when fetching by string fails`() {
		val arr = JsonArray()
		runCatching { arr["0"] }.
			onSuccess { fail() }.
			onFailure { e ->
				assertTrue(e is NoSuchIndexException)
				e as NoSuchIndexException
				assertEquals(arr, e.arr)
				assertEquals(0, e.index)
			}
	}

	@Test(expected = NoSuchIndexException::class)
	fun `getting a non existent index when fetching by int fails`() {
		val arr = JsonArray()
		arr[1]
	}

	@Test(expected = NoSuchIndexException::class)
	fun `getting a non existent index when fetching by int by reverse notation fails`() {
		val arr = JsonArray()
		arr[-1]
	}
}