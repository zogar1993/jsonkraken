package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.deserializer.errors.DeserializationException
import net.jemzart.jsonkraken.JsonValue
import net.jemzart.jsonkraken.deserializer.Deserializer.Companion.BLANK_RAW_STRING
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class BlankDeserialization {
	@Test
	fun `empty string`() {
		runCatching { JsonKraken.deserialize<JsonValue>("") }.
			onSuccess { fail() }.
			onFailure { e ->
				assertTrue(e is DeserializationException)
				e as DeserializationException
				assertEquals(BLANK_RAW_STRING, e.detail)
				assertEquals(0, e.index)
				assertEquals("", e.raw)
				assertEquals("\n^", e.snapshot)
			}
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