package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ValueSerialization {
	@Test
	fun `lonely null`(){
		assert(null.toJsonString() == "null")
	}

	@Test
	fun `lonely true`(){
		assert(true.toJsonString() == "true")
	}

	@Test
	fun `lonely false`(){
		assert(false.toJsonString() == "false")
	}

	@Test
	fun `lonely Int`(){
		assert(5.toJsonString() == "5")
	}

	@Test
	fun `lonely Double`(){
		assert((5.2).toJsonString() == "5.2")
	}

	@Test
	fun `lonely Char`(){
		assert('z'.toJsonString() == "\"z\"")
	}

	@Test
	fun `lonely String`(){
		assert("Rhagost".toJsonString() == "\"Rhagost\"")
	}

	@Test
	fun `contained null`(){
		assert(JsonArray(null).toJsonString() == "[null]")
	}

	@Test
	fun `contained true`(){
		assert(JsonArray(true).toJsonString() == "[true]")
	}

	@Test
	fun `contained false`(){
		assert(JsonArray(false).toJsonString() == "[false]")
	}

	@Test
	fun `contained Int`(){
		assert(JsonArray(5).toJsonString() == "[5]")
	}

	@Test
	fun `contained Double`(){
		assert(JsonArray(5.2).toJsonString() == "[5.2]")
	}

	@Test
	fun `contained Char`(){
		assert(JsonArray('z').toJsonString() == "[\"z\"]")
	}

	@Test
	fun `contained String`(){
		assert(JsonArray("Rhagost").toJsonString() == "[\"Rhagost\"]")
	}

	@Test
	fun `array with two items`(){
		assert(JsonArray(true, false).toJsonString() == "[true,false]")
	}

	@Test
	fun `object with two items`(){
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert(JsonObject("a" to true, "b" to false).toJsonString() == "{$key1:true,$key2:false}")
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> value.toJsonString() }
	}

	@Test
	fun map() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert(mapOf("a" to true, "b" to false).toJsonString() == "{$key1:true,$key2:false}")
	}

	@Test
	fun iterable() {
		assert(listOf(true, false).toJsonString() == "[true,false]")
	}

	@Test
	fun array() {
		assert(arrayOf(true, false).toJsonString() == "[true,false]")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `invalid type`(){
		Exception().toJsonString()
	}
}