package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonObjectIsEmpty {
	@Test
	fun `is empty`() {
		val obj = JsonObject()
		assertTrue(obj.isEmpty())
	}

	@Test
	fun `is not empty`() {
		val obj = JsonObject("" to "a")
		assertTrue(obj.isNotEmpty())
	}
}