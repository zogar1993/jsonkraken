package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class TrueDeserialization {
	@Test
	fun `true`() {
		val json = JsonKraken.deserialize<JsonTrue>("true")
		assertEquals(JsonTrue, json)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled true`() {
		JsonKraken.deserialize<JsonTrue>("tttt")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonTrue>("tru")
	}
}