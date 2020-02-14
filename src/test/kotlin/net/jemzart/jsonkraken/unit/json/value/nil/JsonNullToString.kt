package net.jemzart.jsonkraken.unit.json.value.nil

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonNull
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonNullToString {
	@Test
	fun simple() {
		val nil = JsonNull
		val string = nil.toString()
		val serialized = JsonKraken.serialize(nil)
		assertEquals(serialized, string)
	}
}