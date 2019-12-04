package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializationException
import net.jemzart.jsonkraken.JsonValue
import org.junit.Test

class BlankDeserialization {
	@Test(expected = DeserializationException::class)
	fun `empty string`() {
		JsonKraken.deserialize<JsonValue>("")
	}

	@Test(expected = DeserializationException::class)
	fun `just a space`() {
		JsonKraken.deserialize<JsonValue>(" ")
	}

	@Test(expected = DeserializationException::class)
	fun `just an escaped t`() {
		JsonKraken.deserialize<JsonValue>("\t")
	}

	@Test(expected = DeserializationException::class)
	fun `just an escaped n`() {
		JsonKraken.deserialize<JsonValue>("\n")
	}

	@Test(expected = DeserializationException::class)
	fun `just an escaped r`() {
		JsonKraken.deserialize<JsonValue>("\r")
	}
}