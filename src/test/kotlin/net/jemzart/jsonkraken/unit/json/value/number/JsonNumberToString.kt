package net.jemzart.jsonkraken.unit.json.value.number

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonNumberToString {
	@Test
	fun simple() {
		val number = JsonNumber(0)
		val string = number.toString()
		val serialized = JsonKraken.serialize(number)
		assertEquals(serialized, string)
	}
}