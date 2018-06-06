package jemzart.jsonkraken.unit.json.`object`

import jemzart.jsonkraken.JSON_VALUE
import jemzart.jsonkraken.values.JsonObject
import jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonObjectGetOperator {
	private val insertion: JsonValue = JSON_VALUE

	@Test
	fun byString(){
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj["0", JSON_VALUE] == insertion)
		assert(obj["0"] == insertion)
	}

	@Test
	fun byInt(){
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj[0, JSON_VALUE] == insertion)
		assert(obj[0] == insertion)
	}
}