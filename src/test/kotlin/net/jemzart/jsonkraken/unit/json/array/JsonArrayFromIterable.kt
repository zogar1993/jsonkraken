package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonString
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Test

class JsonArrayFromIterable {
	@Test
	fun `List to JsonArray`() {
		val list = listOf("A", 2.0, true)

		val arr = list.toJsonValue()

		assert(arr[0] == JsonString("A"))
		assert(arr[1] == JsonNumber(2.0))
		assert(arr[2] == JsonTrue)
	}
}