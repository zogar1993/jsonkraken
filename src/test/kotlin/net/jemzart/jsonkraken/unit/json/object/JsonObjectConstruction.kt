package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectConstruction {
	@Test
	fun empty(){
		val obj = JsonObject()

		assert(obj.size == 0)
	}

	@Test
	fun `one property`(){
		val obj = JsonObject("Captain" to "Von Chap")

		assert(obj["Captain"] == "Von Chap")
	}

	@Test
	fun `two properties`(){
		val obj = JsonObject("Captain" to "Von Chap", "Hero" to "Joelin")

		assert(obj["Captain"] == "Von Chap")
		assert(obj["Hero"] == "Joelin")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`(){
		JsonObject("0" to Exception())
	}

	@Test
	fun `json string compliance for keys`() {
		JsonStringCompliance.verify { value: Any -> JsonObject(value.toString() to null) }
	}

	@Test
	fun `json string compliance for values`() {
		JsonStringCompliance.verify { value: Any -> JsonObject("0" to value) }
	}
}