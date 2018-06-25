package com.jemzart.jsonkraken.unit.json.deserialization

import com.jemzart.jsonkraken.helpers.str
import com.jemzart.jsonkraken.helpers.WS
import com.jemzart.jsonkraken.get
import com.jemzart.jsonkraken.toJson
import com.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class ArrayDeserialization{
	@Test
	fun empty(){
		val json = "[]".toJson() as JsonArray
		assert(json.size == 0)
	}

	@Test
	fun allowedWhiteSpaces(){
		val json = "$WS[$WS${str("A")}$WS,$WS${str("B")}$WS]$WS".toJson()
		assert(json[0] == "A")
		assert(json[1] == "B")
	}
}