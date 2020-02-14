package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonObjectToString {
	@Test
	fun simple() {
		val obj = JsonObject()
		val string = obj.toString()
		val serialized = JsonKraken.serialize(obj)
		assertEquals(serialized, string)
	}
}