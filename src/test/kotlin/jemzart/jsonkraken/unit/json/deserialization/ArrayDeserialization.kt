package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.STRING
import jemzart.jsonkraken.toJson
import org.junit.Test

class ArrayDeserialization{
	val ws = " \r\n\t"
	fun str(value: String) = "\"$value\""
	@Test
	fun empty(){
		val json = "[]".toJson()
		assert(json.size == 0)
	}

	@Test
	fun allowedWhiteSpaces(){
		val json = "$ws[$ws${str("A")}$ws,$ws${str("B")}$ws]$ws".toJson()
		assert(json[0, STRING] == "A")
		assert(json[1, STRING] == "B")
	}
}