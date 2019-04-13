package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Test

class TrueDeserialization {
	@Test
	fun `true`() {
		val json = "true".jsonDeserialize()
		assert(json == JsonTrue)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled true`() {
		"tttt".jsonDeserialize()
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"tru".jsonDeserialize()
	}
}