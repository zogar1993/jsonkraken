package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.helpers.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test


class JsonArrayConstruction {
	@Test
	fun empty(){
		val arr = JsonArray()

		assert(arr.size == 0)
	}

	@Test
	fun `one property`(){
		val arr = JsonArray("Von Chap")

		assert(arr[0] == "Von Chap")
	}

	@Test
	fun `two properties`(){
		val arr = JsonArray("Von Chap", "Joelin")

		assert(arr[0] == "Von Chap")
		assert(arr[1] == "Joelin")
	}


	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`(){
		JsonArray(Exception())
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray(value) }
	}
}