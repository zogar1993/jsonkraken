package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ArrayDeserialization{
	@Test
	fun empty(){
		val json = "[]".toJson() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun emptyString(){
		val json = "[\"\"]".toJson() as JsonArray
		assert(json[0] == "")
	}

	@Test
	fun heterogeneous(){
		val json = "[null, 1, \"1\", {}, [], true, false]".toJson() as JsonArray
		assert(json[0] == null)
		assert(json[1] == 1.0)
		assert(json[2] == "1")
		assert(json[3] is JsonObject)
		assert(json[4] is JsonArray)
		assert(json[5] == true)
		assert(json[6] == false)
	}

	@Test
	fun `allowed white spaces`(){
		val json = "$WS[$WS${str("A")}$WS,$WS${str("B")}$WS]$WS".toJson()
		assert(json[0] == "A")
		assert(json[1] == "B")
	}
}