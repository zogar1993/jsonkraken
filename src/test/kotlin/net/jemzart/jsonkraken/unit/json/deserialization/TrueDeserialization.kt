package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class TrueDeserialization {
	@Test
	fun `true`() {
		val json = JSONKraken.deserialize<JsonTrue>("true")
		assertEquals(JsonTrue, json)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled true`() {
		JSONKraken.deserialize<JsonTrue>("tttt")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonTrue>("tru")
	}
}