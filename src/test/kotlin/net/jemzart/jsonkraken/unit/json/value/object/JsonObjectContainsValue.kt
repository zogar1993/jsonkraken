package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.JsonString
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonObjectContainsValue {
	@Test
	fun `contains value`() {
		val obj = JsonObject("" to "a") as Map<String, Any?>
		assertTrue(obj.containsValue(JsonString("a")))//TODO not really what I want
	}

	//TODO WTF kotlin compiler
	@Test
	fun `does not contain value`() {
		val obj = JsonObject() as Map<String, Any?>
		assertFalse(obj.containsValue("a"))
	}
}