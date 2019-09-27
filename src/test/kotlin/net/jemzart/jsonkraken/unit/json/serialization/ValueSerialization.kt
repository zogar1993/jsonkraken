package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ValueSerialization {
	@Test
	fun `lonely null`() {
		val serialized = JsonKraken.serialize(null)
		assertEquals("null", serialized)
	}

	@Test
	fun `lonely true`() {
		val serialized = JsonKraken.serialize(true)
		assertEquals("true", serialized)
	}

	@Test
	fun `lonely false`() {
		val serialized = JsonKraken.serialize(false)
		assertEquals("false", serialized)
	}

	@Test
	fun `lonely Int`() {
		val serialized = JsonKraken.serialize(5)
		assertEquals("5", serialized)
	}

	@Test
	fun `lonely Double`() {
		val serialized = JsonKraken.serialize(5.2)
		assertEquals("5.2", serialized)
	}

	@Test
	fun `lonely Char`() {
		val serialized = JsonKraken.serialize('z')
		assertEquals("\"z\"", serialized)
	}

	@Test
	fun `lonely String`() {
		val serialized = JsonKraken.serialize("Rhagost")
		assertEquals("\"Rhagost\"", serialized)
	}

	@Test
	fun `contained null`() {
		val serialized = JsonKraken.serialize(JsonArray(null))
		assertEquals("[null]", serialized)
	}

	@Test
	fun `contained true`() {
		val serialized = JsonKraken.serialize(JsonArray(true))
		assertEquals("[true]", serialized)
	}

	@Test
	fun `contained false`() {
		val serialized = JsonKraken.serialize(JsonArray(false))
		assertEquals("[false]", serialized)
	}

	@Test
	fun `contained Int`() {
		val serialized = JsonKraken.serialize(JsonArray(5))
		assertEquals("[5]", serialized)
	}

	@Test
	fun `contained Double`() {
		val serialized = JsonKraken.serialize(JsonArray(5.2))
		assertEquals("[5.2]", serialized)
	}

	@Test
	fun `contained Char`() {
		val serialized = JsonKraken.serialize(JsonArray('z'))
		assertEquals("[\"z\"]", serialized)
	}

	@Test
	fun `contained String`() {
		val serialized = JsonKraken.serialize(JsonArray("Rhagost"))
		assertEquals("[\"Rhagost\"]", serialized)
	}

	@Test
	fun `array with two items`() {
		val serialized = JsonKraken.serialize(JsonArray(true, false))
		assertEquals("[true,false]", serialized)
	}

	@Test
	fun `object with two items`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		val serialized = JsonKraken.serialize(JsonObject("a" to true, "b" to false))
		assertEquals("{$key1:true,$key2:false}", serialized)
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonKraken.serialize(value) }
	}

	@Test
	fun map() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		val serialized = JsonKraken.serialize(mapOf("a" to true, "b" to false))
		assertEquals("{$key1:true,$key2:false}", serialized)
	}

	@Test
	fun iterable() {
		val serialized = JsonKraken.serialize(listOf(true, false))
		assertEquals("[true,false]", serialized)
	}

	@Test
	fun array() {
		val serialized = JsonKraken.serialize(arrayOf(true, false))
		assertEquals("[true,false]", serialized)
	}
}