package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class ValueSerialization {
	@Test
	fun lonelyNull(){
		assert(null.toJsonString() == "null")
	}

	@Test
	fun lonelyTrue(){
		assert(true.toJsonString() == "true")
	}

	@Test
	fun lonelyFalse(){
		assert(false.toJsonString() == "false")
	}

	@Test
	fun lonelyInteger(){
		assert(5.toJsonString() == "5")
	}

	@Test
	fun lonelyDouble(){
		assert((5.2).toJsonString() == "5.2")
	}

	@Test
	fun lonelyChar(){
		assert('z'.toJsonString() == "\"z\"")
	}

	@Test
	fun lonelyString(){
		assert("Rhagost".toJsonString() == "\"Rhagost\"")
	}

	@Test
	fun containedNull(){
		assert(JsonArray(null).toJsonString() == "[null]")
	}

	@Test
	fun containedTrue(){
		assert(JsonArray(true).toJsonString() == "[true]")
	}

	@Test
	fun containedFalse(){
		assert(JsonArray(false).toJsonString() == "[false]")
	}

	@Test
	fun containedInteger(){
		assert(JsonArray(5).toJsonString() == "[5]")
	}

	@Test
	fun containedDouble(){
		assert(JsonArray(5.2).toJsonString() == "[5.2]")
	}

	@Test
	fun containedChar(){
		assert(JsonArray('z').toJsonString() == "[\"z\"]")
	}

	@Test
	fun containedString(){
		assert(JsonArray("Rhagost").toJsonString() == "[\"Rhagost\"]")
	}
}