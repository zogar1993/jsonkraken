package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializingBlankStringException
import net.jemzart.jsonkraken.deserializer.errors.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonValue
import org.junit.Test

class BlankDeserialization {
	@Test(expected = DeserializingBlankStringException::class)
	fun `empty string`() {
		JsonKraken.deserialize<JsonValue>("")
	}

	@Test(expected = DeserializingBlankStringException::class)
	fun `just a space`() {
		JsonKraken.deserialize<JsonValue>(" ")
	}

	@Test(expected = DeserializingBlankStringException::class)
	fun `just an escaped t`() {
		JsonKraken.deserialize<JsonValue>("\t")
	}

	@Test(expected = DeserializingBlankStringException::class)
	fun `just an escaped n`() {
		JsonKraken.deserialize<JsonValue>("\n")
	}

	@Test(expected = DeserializingBlankStringException::class)
	fun `just an escaped r`() {
		JsonKraken.deserialize<JsonValue>("\r")
	}
}