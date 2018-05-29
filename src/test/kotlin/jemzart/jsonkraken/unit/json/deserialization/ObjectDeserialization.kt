package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.STRING
import jemzart.jsonkraken.helpers.WS
import jemzart.jsonkraken.helpers.str
import jemzart.jsonkraken.toJson
import org.junit.Test

class ObjectDeserialization{
	@Test
	fun empty(){
		val json = "[]".toJson()
		assert(json.size == 0)
	}

	@Test
	fun allowedWhiteSpaces(){
		val json = "$WS{$WS${str("0")}$WS:$WS${str("A")}$WS,$WS${str("1")}$WS:$WS${str("B")}$WS}$WS".toJson()
		assert(json["0", STRING] == "A")
		assert(json["1", STRING] == "B")
	}
}