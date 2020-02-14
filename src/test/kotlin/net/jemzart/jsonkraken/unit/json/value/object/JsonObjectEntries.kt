package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonObjectEntries {
	@Test
	fun simple() {
		val obj = JsonObject()
		assertEquals(obj.hashMap, obj.entries)
	}
}