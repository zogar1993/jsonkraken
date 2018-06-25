package com.jemzart.jsonkraken.unit.json.deserialization

import com.jemzart.jsonkraken.helpers.WS
import com.jemzart.jsonkraken.get
import com.jemzart.jsonkraken.helpers.str
import com.jemzart.jsonkraken.toJson
import com.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ObjectDeserialization{
	@Test
	fun empty(){
		val json = "{}".toJson() as JsonObject
		assert(json.size == 0)
	}

	@Test
	fun allowedWhiteSpaces(){
		val json = "$WS{$WS${str("0")}$WS:$WS${str("A")}$WS,$WS${str("1")}$WS:$WS${str("B")}$WS}$WS".toJson()
		assert(json["0"] == "A")
		assert(json["1"] == "B")
	}
}