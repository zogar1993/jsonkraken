package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArrayAdd {

	@Test
	fun first(){
		val arr = JsonArray()

		arr.add("new")

		assert(arr[0] == "new")
	}

	@Test
	fun second(){
		val arr = JsonArray(0)

		arr.add("new")

		assert(arr[1] == "new")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun failsOnInvalidType(){
		val arr = JsonArray()

		arr.add(Exception())
	}

	@Test(expected = CircularReferenceException::class)
	fun circularReferenceNotAllowed(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		arr.add(obj)
	}

	@Test(expected = CircularReferenceException::class)
	fun selfReferenceNotAllowed(){
		val arr = JsonArray()

		arr.add(arr)
	}
}