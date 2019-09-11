package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonNull
import org.junit.Assert.assertEquals
import org.junit.Test

class NullDeserialization {
	@Test
	fun `null`() {
		val json = JSONKraken.deserialize<JsonNull>("null")
		assertEquals(JsonNull, json)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled null`() {
		JSONKraken.deserialize<JsonNull>("nnnn")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonNull>("nul")
	}
}