package net.jemzart.jsonkraken.unit.json.value.array

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonKraken
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonArrayToString {
	@Test
	fun simple() {
		val arr = JsonArray()
		val string = arr.toString()
		val serialized = JsonKraken.serialize(arr)
		assertEquals(serialized, string)
	}
}