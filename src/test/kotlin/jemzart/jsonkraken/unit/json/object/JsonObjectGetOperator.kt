package jemzart.jsonkraken.unit.json.`object`

import jemzart.jsonkraken.JSON_VALUE
import jemzart.jsonkraken.values.JsonObject
import jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonObjectGetOperator {
	val insertion: JsonValue = JSON_VALUE

	@Test
	fun byString(){
		val arr = JsonObject()

		arr["0"] = insertion

		assert(arr["0", JSON_VALUE] == insertion)
		assert(arr["0"] == insertion)
	}

	@Test
	fun byInt(){
		val arr = JsonObject()

		arr["0"] = insertion

		assert(arr[0, JSON_VALUE] == insertion)
		assert(arr[0] == insertion)
	}
}