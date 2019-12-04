package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializationException
import net.jemzart.jsonkraken.JsonNull

import org.junit.Assert.assertEquals
import org.junit.Test

class NullDeserialization {
	@Test
	fun `null`() {
		val json = JsonKraken.deserialize<JsonNull>("null")
		assertEquals(JsonNull, json)
	}

	@Test(expected = DeserializationException::class)
	fun `misspelled null`() {
		JsonKraken.deserialize<JsonNull>("nnnn")
	}

	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonNull>("nul")
	}
}