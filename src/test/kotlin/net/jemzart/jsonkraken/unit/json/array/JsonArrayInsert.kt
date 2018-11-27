package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.helpers.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArrayInsert{

	@Test
	fun `inserted between`(){
		val arr = JsonArray(0.0, 1.0)

		arr.insert(1, "new")

		assert(arr[0] == 0.0)
		assert(arr[1] == "new")
		assert(arr[2] == 1.0)
	}

	@Test
	fun `inserted after`(){
		val arr = JsonArray(0)

		arr.insert(1, "new")

		assert(arr[1] == "new")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`(){
		val arr = JsonArray()

		arr.insert(0, Exception())
	}

	@Test(expected = CircularReferenceException::class)
	fun `circular reference not allowed`(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		arr.insert(0, obj)
	}

	@Test(expected = CircularReferenceException::class)
	fun `self reference not allowed`(){
		val arr = JsonArray()

		arr.insert(0, arr)
	}

	@Test
	fun `reverse notation`(){
		val arr = JsonArray(0.0, 1.0)

		arr.insert(-1, "new")

		assert(arr[0] == 0.0)
		assert(arr[1] == "new")
		assert(arr[2] == 1.0)
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray().insert(0, value) }
	}
}