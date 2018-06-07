package jemzart.jsonkraken.unit.json.serialization

import jemzart.jsonkraken.toJsonString
import org.junit.Test

class ValueSerialization {
	@Test
	fun nullSerialization(){
		assert(null.toJsonString() == "null")
	}
}