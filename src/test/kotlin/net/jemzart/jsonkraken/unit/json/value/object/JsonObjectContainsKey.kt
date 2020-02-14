package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonObjectContainsKey {
	@Test
	fun `contains key`() {
		val obj = JsonObject("a" to "")
		assertTrue(obj.containsKey("a"))
	}

	@Test
	fun `does not contain key`() {
		val obj = JsonObject()
		assertFalse(obj.containsKey("a"))
	}
}