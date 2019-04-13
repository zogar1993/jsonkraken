package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import org.junit.Test

class StringDeserialization {
	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"\"".jsonDeserialize()
	}
}