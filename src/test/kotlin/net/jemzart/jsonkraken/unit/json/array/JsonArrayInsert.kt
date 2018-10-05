package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArrayInsert{

	@Test
	fun insertedBetween(){
		val arr = JsonArray(0.0, 1.0)

		arr.insert(1, "new")

		assert(arr[0] == 0.0)
		assert(arr[1] == "new")
		assert(arr[2] == 1.0)
	}

	@Test
	fun insertedAfter(){
		val arr = JsonArray(0)

		arr.insert(1, "new")

		assert(arr[1] == "new")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun failsOnInvalidType(){
		val arr = JsonArray()

		arr.insert(0, Exception())
	}

	@Test(expected = CircularReferenceException::class)
	fun circularReferenceNotAllowed(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		arr.insert(0, obj)
	}

	@Test(expected = CircularReferenceException::class)
	fun selfReferenceNotAllowed(){
		val arr = JsonArray()

		arr.insert(0, arr)
	}
}