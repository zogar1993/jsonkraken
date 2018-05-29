package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.NULLABLE_ANY
import jemzart.jsonkraken.toJson
import org.junit.Test

class NullDeserialization{
	@Test
	fun trueValue(){
		val json = "[null]".toJson()
		assert(json[0, NULLABLE_ANY] == null)
	}

	@Test(expected = Throwable::class)
	fun misspelledNull() {
		"nnnn".toJson()
	}
}