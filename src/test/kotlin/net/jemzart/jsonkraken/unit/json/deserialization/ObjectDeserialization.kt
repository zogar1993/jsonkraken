package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.utils.WS
import net.jemzart.jsonkraken.utils.str
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ObjectDeserialization{
	@Test
	fun empty(){
		val json = "{}".toJson() as JsonObject
		assert(json.size == 0)
	}

	@Test
	fun `one element`(){
		val json = "{\"a\":\"b\"}".toJson() as JsonObject
		assert(json["a"] == "b")
	}

	@Test
	fun `two elements`(){
		val json = "{\"a\":\"b\", \"c\":\"d\"}".toJson() as JsonObject
		assert(json["a"] == "b")
		assert(json["c"] == "d")
	}

	@Test
	fun `duplicate key and value leaves just one of them`(){
		val json = "{\"a\":\"b\",\"a\":\"b\"}".toJson() as JsonObject
		assert(json["a"] == "b")
		assert(json.size == 1)
	}

	@Test
	fun `duplicate key leaves last`(){
		val json = "{\"a\":\"b\",\"a\":\"c\"}".toJson() as JsonObject
		assert(json["a"] == "c")
		assert(json.size == 1)
	}

	@Test
	fun `empty key`(){
		val json = "{\"\":0}".toJson() as JsonObject
		assert(json[""] == 0.0)
	}

	@Test
	fun `escaped null in key`(){
		val json = "{\"foo\\u0000bar\": 42}".toJson() as JsonObject
		assert(json["foo\\u0000bar"] == 42.0)
	}

	@Test
	fun `allowed white spaces`(){
		val json = "$WS{$WS${str("0")}$WS:$WS${str("A")}$WS,$WS${str("1")}$WS:$WS${str("B")}$WS}$WS".toJson()
		assert(json["0"] == "A")
		assert(json["1"] == "B")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`(){
		"{".toJson()
	}
}