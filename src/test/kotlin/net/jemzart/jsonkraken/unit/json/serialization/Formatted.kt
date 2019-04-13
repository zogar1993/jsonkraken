package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.jsonSerialize
import org.junit.Test

class Formatted {
	@Test
	fun lonely() {
		assert("true".jsonDeserialize().jsonSerialize(true) == "true")
	}

	@Test
	fun `empty array`() {
		assert("[]".jsonDeserialize().jsonSerialize(true) == "[\n\n]")
	}

	@Test
	fun `empty object`() {
		assert("{}".jsonDeserialize().jsonSerialize(true) == "{\n\n}")
	}

	@Test
	fun `array 1 element`() {
		assert("[true]".jsonDeserialize().jsonSerialize(true)
			== "[\n\ttrue\n]")
	}

	@Test
	fun `array 2 elements`() {
		assert("[true,false]".jsonDeserialize().jsonSerialize(true)
			== "[\n\ttrue,\n\tfalse\n]")
	}

	@Test
	fun `object 1 element`() {
		val key1 = "\"a\""
		assert("{\"a\":true}".jsonDeserialize().jsonSerialize(true)
			== "{\n\t$key1: true\n}")
	}

	@Test
	fun `object 2 elements`() {
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert("{$key1:true,$key2:false}".jsonDeserialize().jsonSerialize(true)
			== "{\n\t$key1: true,\n\t$key2: false\n}")
	}
}