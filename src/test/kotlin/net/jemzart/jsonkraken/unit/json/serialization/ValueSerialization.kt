package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.jsonSerialize
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ValueSerialization {
	@Test
	fun `lonely null`() {
		assert(null.jsonSerialize() == "null")
	}

	@Test
	fun `lonely true`() {
		assert(true.jsonSerialize() == "true")
	}

	@Test
	fun `lonely false`() {
		assert(false.jsonSerialize() == "false")
	}

	@Test
	fun `lonely Int`() {
		assert(5.jsonSerialize() == "5")
	}

	@Test
	fun `lonely Double`() {
		assert((5.2).jsonSerialize() == "5.2")
	}

	@Test
	fun `lonely Char`() {
		assert('z'.jsonSerialize() == "\"z\"")
	}

	@Test
	fun `lonely String`() {
		assert("Rhagost".jsonSerialize() == "\"Rhagost\"")
	}

	@Test
	fun `contained null`() {
		assert(JsonArray(null).jsonSerialize() == "[null]")
	}

	@Test
	fun `contained true`() {
		assert(JsonArray(true).jsonSerialize() == "[true]")
	}

	@Test
	fun `contained false`() {
		assert(JsonArray(false).jsonSerialize() == "[false]")
	}

	@Test
	fun `contained Int`() {
		assert(JsonArray(5).jsonSerialize() == "[5]")
	}

	@Test
	fun `contained Double`() {
		assert(JsonArray(5.2).jsonSerialize() == "[5.2]")
	}

	@Test
	fun `contained Char`() {
		assert(JsonArray('z').jsonSerialize() == "[\"z\"]")
	}

	@Test
	fun `contained String`() {
		assert(JsonArray("Rhagost").jsonSerialize() == "[\"Rhagost\"]")
	}

	@Test
	fun `array with two items`() {
		assert(JsonArray(true, false).jsonSerialize() == "[true,false]")
	}

	@Test
	fun `object with two items`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert(JsonObject("a" to true, "b" to false).jsonSerialize() == "{$key1:true,$key2:false}")
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> value.jsonSerialize() }
	}

	@Test
	fun map() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert(mapOf("a" to true, "b" to false).jsonSerialize() == "{$key1:true,$key2:false}")
	}

	@Test
	fun iterable() {
		assert(listOf(true, false).jsonSerialize() == "[true,false]")
	}

	@Test
	fun array() {
		assert(arrayOf(true, false).jsonSerialize() == "[true,false]")
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `invalid type`() {
		Exception().jsonSerialize()
	}
}