package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.get
import jemzart.jsonkraken.toJson
import org.junit.Test

class NullDeserialization{
	@Test
	fun trueValue(){
		val json = "null".toJson()
		assert(json == null)
	}

	@Test(expected = Throwable::class)
	fun misspelledNull() {
		"nnnn".toJson()
	}
}