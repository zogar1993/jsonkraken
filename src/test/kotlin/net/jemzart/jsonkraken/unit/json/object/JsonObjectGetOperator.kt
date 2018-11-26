package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonObjectGetOperator {
	private val insertion: JsonValue = JsonObject()

	@Test
	fun `by String`(){
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj["0"] == insertion)
		assert(obj["0"] == insertion)
	}

	@Test
	fun `by Int`(){
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj[0] == insertion)
		assert(obj[0] == insertion)
	}
}