package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonNumber
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonString


import org.junit.Test

class JsonArrayInsert {

	@Test
	fun `inserted between`() {
		val arr = JsonArray(0.0, 1.0)

		arr.insert(1, "new")

		assert(arr[0] == JsonNumber(0.0))
		assert(arr[1] == JsonString("new"))
		assert(arr[2] == JsonNumber(1.0))
	}

	@Test
	fun `inserted after`() {
		val arr = JsonArray(0)

		arr.insert(1, "new")

		assert(arr[1] == JsonString("new"))
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`() {
		val arr = JsonArray()

		arr.insert(0, Exception())
	}

	@Test
	fun `circular reference not allowed`() {
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		runCatching { arr.insert(0, obj) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == obj)
		}
	}

	@Test
	fun `self reference not allowed`() {
		val arr = JsonArray()

		runCatching { arr.insert(0, arr) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `reverse notation`() {
		val arr = JsonArray(0.0, 1.0)

		arr.insert(-1, "new")

		assert(arr[0] == JsonNumber(0.0))
		assert(arr[1] == JsonString("new"))
		assert(arr[2] == JsonNumber(1.0))
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray().insert(0, value) }
	}
}