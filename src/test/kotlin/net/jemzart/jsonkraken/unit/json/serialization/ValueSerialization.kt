package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.toJsonValue
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ValueSerialization {
	@Test
	fun `lonely null`() {
		val serialized = JSONKraken.serialize(null)
		assertEquals("null", serialized)
	}

	@Test
	fun `lonely true`() {
		val serialized = JSONKraken.serialize(true)
		assertEquals("true", serialized)
	}

	@Test
	fun `lonely false`() {
		val serialized = JSONKraken.serialize(false)
		assertEquals("false", serialized)
	}

	@Test
	fun `lonely Int`() {
		val serialized = JSONKraken.serialize(5)
		assertEquals("5", serialized)
	}

	@Test
	fun `lonely Double`() {
		val serialized = JSONKraken.serialize(5.2)
		assertEquals("5.2", serialized)
	}

	@Test
	fun `lonely Char`() {
		val serialized = JSONKraken.serialize('z')
		assertEquals("\"z\"", serialized)
	}

	@Test
	fun `lonely String`() {
		val serialized = JSONKraken.serialize("Rhagost")
		assertEquals("\"Rhagost\"", serialized)
	}

	@Test
	fun `contained null`() {
		val serialized = JSONKraken.serialize(JsonArray(null))
		assertEquals("[null]", serialized)
	}

	@Test
	fun `contained true`() {
		val serialized = JSONKraken.serialize(JsonArray(true))
		assertEquals("[true]", serialized)
	}

	@Test
	fun `contained false`() {
		val serialized = JSONKraken.serialize(JsonArray(false))
		assertEquals("[false]", serialized)
	}

	@Test
	fun `contained Int`() {
		val serialized = JSONKraken.serialize(JsonArray(5))
		assertEquals("[5]", serialized)
	}

	@Test
	fun `contained Double`() {
		val serialized = JSONKraken.serialize(JsonArray(5.2))
		assertEquals("[5.2]", serialized)
	}

	@Test
	fun `contained Char`() {
		val serialized = JSONKraken.serialize(JsonArray('z'))
		assertEquals("[\"z\"]", serialized)
	}

	@Test
	fun `contained String`() {
		val serialized = JSONKraken.serialize(JsonArray("Rhagost"))
		assertEquals("[\"Rhagost\"]", serialized)
	}

	@Test
	fun `array with two items`() {
		val serialized = JSONKraken.serialize(JsonArray(true, false))
		assertEquals("[true,false]", serialized)
	}

	@Test
	fun `object with two items`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		val serialized = JSONKraken.serialize(JsonObject("a" to true, "b" to false))
		assertEquals("{$key1:true,$key2:false}", serialized)
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JSONKraken.serialize(value) }
	}

	@Test
	fun map() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		val serialized = JSONKraken.serialize(mapOf("a" to true, "b" to false))
		assertEquals("{$key1:true,$key2:false}", serialized)
	}

	@Test
	fun iterable() {
		val serialized = JSONKraken.serialize(listOf(true, false))
		assertEquals("[true,false]", serialized)
	}

	@Test
	fun array() {
		val serialized = JSONKraken.serialize(arrayOf(true, false))
		assertEquals("[true,false]", serialized)
	}

	//TODO Tests Mezclados
	@Test(expected = InvalidJsonTypeException::class)
	fun `invalid type`() {
		Exception().toJsonValue()
	}
}