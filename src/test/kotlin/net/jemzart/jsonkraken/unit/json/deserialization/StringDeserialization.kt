package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonString
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Test

class StringDeserialization {
	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonString>("\"")
	}
}