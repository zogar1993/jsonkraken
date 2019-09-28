package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonNull
import org.junit.Assert.assertEquals
import org.junit.Test

class NullDeserialization {
	@Test
	fun `null`() {
		val json = JsonKraken.deserialize<JsonNull>("null")
		assertEquals(JsonNull, json)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled null`() {
		JsonKraken.deserialize<JsonNull>("nnnn")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonNull>("nul")
	}
}