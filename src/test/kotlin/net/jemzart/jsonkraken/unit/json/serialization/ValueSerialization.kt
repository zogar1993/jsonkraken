package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
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
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> value.toJsonString() }
	}
}