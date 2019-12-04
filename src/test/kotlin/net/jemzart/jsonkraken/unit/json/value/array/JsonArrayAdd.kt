package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString


import org.junit.Test

class JsonArrayAdd {
	@Test
	fun first() {
		val arr = JsonArray()

		arr.add("new")

		assert(arr[0] == JsonString("new"))
	}

	@Test
	fun second() {
		val arr = JsonArray(0)

		arr.add("new")

		assert(arr[1] == JsonString("new"))
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`() {
		val arr = JsonArray()

		arr.add(Exception())
	}

	@Test
	fun `circular reference not allowed`() {
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		runCatching { arr.add(obj) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == obj)
		}
	}

	@Test
	fun `self reference not allowed`() {
		val arr = JsonArray()

		runCatching { arr.add(arr) }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == arr)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray().add(value) }
	}
}