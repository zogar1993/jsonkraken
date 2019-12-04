package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.exceptions.NoSuchIndexException
import net.jemzart.jsonkraken.JsonArray
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

	@Test(expected = NoSuchIndexException::class)
	fun `getting a non existent index when fetching by string fails`() {
		val obj = JsonArray()
		obj["0"]
	}

	@Test(expected = NoSuchIndexException::class)
	fun `getting a non existent index when fetching by int fails`() {
		val obj = JsonArray()
		obj[1]
	}

	@Test(expected = NoSuchIndexException::class)
	fun `getting a non existent index when fetching by int by reverse notation fails`() {
		val obj = JsonArray()
		obj[-1]
	}
}