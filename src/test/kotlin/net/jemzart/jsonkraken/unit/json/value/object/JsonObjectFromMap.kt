package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class JsonObjectFromMap {
	@Test
	fun `Map to JsonObject`() {
		val map = mapOf("A" to 10.0, "B" to "ten")

		val arr = map.toJsonValue()

		assert(arr["A"] == JsonNumber(10.0))
		assert(arr["B"] == JsonString("ten"))
	}
}