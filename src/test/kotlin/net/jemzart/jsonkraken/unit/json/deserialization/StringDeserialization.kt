package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class StringDeserialization {
	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonString>("\"")
	}
}