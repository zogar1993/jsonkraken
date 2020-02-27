package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonString
import net.jemzart.jsonkraken.errors.deserialization.DeserializationException
import org.junit.Test

class StringDeserialization {
	@Test(expected = DeserializationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonString>("\"")
	}
}