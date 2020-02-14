package net.jemzart.jsonkraken.unit.json.value.`object`


import net.jemzart.jsonkraken.JsonObject
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonObjectIsEmpty {
	@Test
	fun `is empty`() {
		val obj = JsonObject()
		Assert.assertTrue(obj.isEmpty())
	}

	@Test
	fun `is not empty`() {
		val obj = JsonObject("" to "a")
		Assert.assertFalse(obj.isNotEmpty())
	}
}