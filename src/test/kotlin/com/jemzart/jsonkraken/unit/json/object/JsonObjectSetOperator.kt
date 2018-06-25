package com.jemzart.jsonkraken.unit.json.`object`

import com.jemzart.jsonkraken.values.JsonObject
import com.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class JsonObjectSetOperator{
	private val insertion: JsonValue = JsonObject()

	@Test
	fun byString(){
		val obj = JsonObject()
		obj["0"] = insertion
		assert(obj["0"] == insertion)
	}

	@Test
	fun byInt(){
		val obj = JsonObject()
		obj[0] = insertion
		assert(obj["0"] == insertion)
	}
}