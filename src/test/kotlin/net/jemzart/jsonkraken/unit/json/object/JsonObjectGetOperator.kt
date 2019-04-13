package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectGetOperator {
	private val insertion: JsonContainer = JsonObject()

	@Test
	fun `by String`() {
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj["0"] == insertion)
		assert(obj["0"] == insertion)
	}

	@Test
	fun `by Int`() {
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj[0] == insertion)
		assert(obj[0] == insertion)
	}
}