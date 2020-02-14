package net.jemzart.jsonkraken.unit.json.value.string

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonString
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonStringToString {
	@Test
	fun simple() {
		val str = JsonString("")
		val string = str.toString()
		val serialized = JsonKraken.serialize(str)
		assertEquals(serialized, string)
	}
}