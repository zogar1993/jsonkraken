package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.values.JsonNull
import org.junit.Test

class NullDeserialization {
	@Test
	fun `null`() {
		val json = "null".jsonDeserialize()
		assert(json == JsonNull)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled null`() {
		"nnnn".jsonDeserialize()
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"nul".jsonDeserialize()
	}
}