package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.helpers.str
import jemzart.jsonkraken.toJson
import jemzart.jsonkraken.values.JsonNonCollection
import org.junit.Test

class NonCollectionDeserialization{
	@Test
	fun lonelyFalse(){
		val value = ("false".toJson() as JsonNonCollection).value
		assert(value == false)
	}

	@Test
	fun lonelyTrue(){
		val value = ("true".toJson() as JsonNonCollection).value
		assert(value == true)
	}

	@Test
	fun lonelyString(){
		val value = (str("Von Chap").toJson() as JsonNonCollection).value
		assert(value == "Von Chap")
	}

	@Test
	fun lonelyNull(){
		val value = ("null".toJson() as JsonNonCollection).value
		assert(value == null)
	}

	@Test
	fun lonelyInteger(){
		val value = ("1".toJson() as JsonNonCollection).value
		assert(value == 1)
	}

	@Test
	fun lonelyDouble(){
		val value = ("1.1".toJson() as JsonNonCollection).value
		assert(value == 1.1)
	}
}