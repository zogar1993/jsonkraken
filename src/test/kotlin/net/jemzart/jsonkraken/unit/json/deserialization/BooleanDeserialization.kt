package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.toJson
import org.junit.Test

class BooleanDeserialization{
	@Test
	fun trueValue(){
		val json = "true".toJson()
		assert(json == true)
	}

	@Test
	fun falseValue(){
		val json = "false".toJson()
		assert(json == false)
	}

	@Test(expected = Throwable::class)
	fun misspelledTrue() {
		"tttt".toJson()
	}

	@Test(expected = Throwable::class)
	fun misspelledFalse() {
		"fffff".toJson()
	}
}