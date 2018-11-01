package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.toJsonString
import org.junit.Test

class ValueSerialization {
	@Test
	fun nullSerialization(){
		assert(null.toJsonString() == "null")
	}

	@Test
	fun trueSerialization(){
		assert(true.toJsonString() == "true")
	}

	@Test
	fun falseSerialization(){
		assert(false.toJsonString() == "false")
	}

	@Test
	fun integerSerialization(){
		assert(5.toJsonString() == "5")
	}

	@Test
	fun doubleSerialization(){
		assert((5.2).toJsonString() == "5.2")
	}

	@Test
	fun charSerialization(){
		assert('z'.toJsonString() == "\"z\"")
	}

	@Test
	fun stringSerialization(){
		assert("Rhagost".toJsonString() == "\"Rhagost\"")
	}
}