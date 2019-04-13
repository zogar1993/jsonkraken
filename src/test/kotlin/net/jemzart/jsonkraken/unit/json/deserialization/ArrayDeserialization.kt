package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.values.*
import org.junit.Test

class ArrayDeserialization {
	@Test
	fun empty() {
		val json = "[]".jsonDeserialize() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun emptyString() {
		val json = "[\"\"]".jsonDeserialize() as JsonArray
		assert(json[0] == JsonString(""))
	}

	@Test
	fun heterogeneous() {
		val json = "[null, 1, \"1\", {}, [], true, false]".jsonDeserialize() as JsonArray
		assert(json[0] == JsonNull)
		assert(json[1] == JsonNumber(1.0))
		assert(json[2] == JsonString("1"))
		assert(json[3] is JsonObject)
		assert(json[4] is JsonArray)
		assert(json[5] == JsonTrue)
		assert(json[6] == JsonFalse)
	}

	@Test
	fun `allowed white spaces`() {
		val json = "$WS[$WS${str("A")}$WS,$WS${str("B")}$WS]$WS".jsonDeserialize()
		assert(json[0] == JsonString("A"))
		assert(json[1] == JsonString("B"))
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"[.".jsonDeserialize()
	}
}