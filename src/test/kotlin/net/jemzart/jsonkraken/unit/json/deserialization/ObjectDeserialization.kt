package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.values.JsonNumber
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class ObjectDeserialization {
	@Test
	fun empty() {
		val json = "{}".jsonDeserialize() as JsonObject
		assert(json.size == 0)
	}

	@Test
	fun `one element`() {
		val json = "{\"a\":\"b\"}".jsonDeserialize() as JsonObject
		assert(json["a"] == JsonString("b"))
	}

	@Test
	fun `two elements`() {
		val json = "{\"a\":\"b\", \"c\":\"d\"}".jsonDeserialize() as JsonObject
		assert(json["a"] == JsonString("b"))
		assert(json["c"] == JsonString("d"))
	}

	@Test
	fun `duplicate key and value leaves just one of them`() {
		val json = "{\"a\":\"b\",\"a\":\"b\"}".jsonDeserialize() as JsonObject
		assert(json["a"] == JsonString("b"))
		assert(json.size == 1)
	}

	@Test
	fun `duplicate key leaves last`() {
		val json = "{\"a\":\"b\",\"a\":\"c\"}".jsonDeserialize() as JsonObject
		assert(json["a"] == JsonString("c"))
		assert(json.size == 1)
	}

	@Test
	fun `empty key`() {
		val json = "{\"\":0}".jsonDeserialize() as JsonObject
		assert(json[""] == JsonNumber(0.0))
	}

	@Test
	fun `escaped null in key`() {
		val json = "{\"foo\\u0000bar\": 42}".jsonDeserialize() as JsonObject
		assert(json["foo\\u0000bar"] == JsonNumber(42.0))
	}

	@Test
	fun `allowed white spaces`() {
		val json = "$WS{$WS${str("0")}$WS:$WS${str("A")}$WS,$WS${str("1")}$WS:$WS${str("B")}$WS}$WS".jsonDeserialize()
		assert(json["0"] == JsonString("A"))
		assert(json["1"] == JsonString("B"))
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"{".jsonDeserialize()
	}
}