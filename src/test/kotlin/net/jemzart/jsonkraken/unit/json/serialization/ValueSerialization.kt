package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.toJsonString
import org.junit.Test

class ValueSerialization {
	@Test
	fun nullSerialization(){
		assert(null.toJsonString() == "null")
	}
}