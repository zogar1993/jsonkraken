package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class ValueSerialization {
	@Test
	fun `lonely Null`(){
		assert(null.toJsonString() == "null")
	}

	@Test
	fun `lonely True`(){
		assert(true.toJsonString() == "true")
	}

	@Test
	fun `lonely False`(){
		assert(false.toJsonString() == "false")
	}

	@Test
	fun `lonely Integer`(){
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
	fun `contained Null`(){
		assert(JsonArray(null).toJsonString() == "[null]")
	}

	@Test
	fun `contained True`(){
		assert(JsonArray(true).toJsonString() == "[true]")
	}

	@Test
	fun `contained False`(){
		assert(JsonArray(false).toJsonString() == "[false]")
	}

	@Test
	fun `contained Integer`(){
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
}