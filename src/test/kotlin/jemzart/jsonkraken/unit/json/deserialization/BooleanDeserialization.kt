package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.BOOLEAN
import jemzart.jsonkraken.toJson
import org.junit.Test

class BooleanDeserialization{
	@Test
	fun trueValue(){
		val json = "[true]".toJson()
		assert(json[0, BOOLEAN])
	}

	@Test
	fun falseValue(){
		val json = "[false]".toJson()
		assert(!json[0, BOOLEAN])
	}

	@Test(expected = Throwable::class)
	fun misspelledTrue() {
		"[tttt]".toJson()
	}

	@Test(expected = Throwable::class)
	fun misspelledFalse() {
		"[fffff]".toJson()
	}
}