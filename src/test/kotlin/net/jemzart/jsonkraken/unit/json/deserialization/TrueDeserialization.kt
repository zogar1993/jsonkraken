package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonTrue
import net.jemzart.jsonkraken.errors.deserialization.DeserializationException
import org.junit.Assert.assertEquals
import org.junit.Test

class TrueDeserialization {
	@Test
	fun `true`() {
		val json = JsonKraken.deserialize<JsonTrue>("true")
		assertEquals(JsonTrue, json)
	}

	@Test(expected = DeserializationException::class)
	fun `misspelled true`() {
		JsonKraken.deserialize<JsonTrue>("tttt")
	}

	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonTrue>("tru")
	}
}