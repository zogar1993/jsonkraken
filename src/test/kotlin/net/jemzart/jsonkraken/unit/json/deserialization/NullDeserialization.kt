package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class NullDeserialization{
	@Test
	fun trueValue(){
		val json = "null".toJson()
		assert(json == null)
	}

	@Test(expected = TokenExpectationException::class)
	fun misspelledNull() {
		"nnnn".toJson()
	}
}