package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectConstruction {
	@Test
	fun empty(){
		val obj = JsonObject()

		assert(obj.size == 0)
	}

	@Test
	fun oneProperty(){
		val obj = JsonObject("Captain" to "Von Chap")

		assert(obj["Captain"] == "Von Chap")
	}

	@Test
	fun twoProperties(){
		val obj = JsonObject("Captain" to "Von Chap", "Hero" to "Joelin")

		assert(obj["Captain"] == "Von Chap")
		assert(obj["Hero"] == "Joelin")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun failsOnInvalidType(){
		JsonObject("0" to Exception())
	}
}