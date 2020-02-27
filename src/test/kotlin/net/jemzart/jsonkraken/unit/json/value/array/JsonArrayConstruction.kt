package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.errors.transformation.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import org.junit.Test


class JsonArrayConstruction {
	@Test
	fun empty() {
		val arr = JsonArray()

		assert(arr.size == 0)
	}

	@Test
	fun `one property`() {
		val arr = JsonArray("Von Chap")

		assert(arr[0] == JsonString("Von Chap"))
	}

	@Test
	fun `two properties`() {
		val arr = JsonArray("Von Chap", "Joelin")

		assert(arr[0] == JsonString("Von Chap"))
		assert(arr[1] == JsonString("Joelin"))
	}


	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`() {
		JsonArray(Exception())
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray(value) }
	}
}