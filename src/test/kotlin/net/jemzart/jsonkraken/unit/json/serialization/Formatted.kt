package net.jemzart.jsonkraken.unit.json.serialization

import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import org.junit.Test

class Formatted {
	@Test
	fun lonely(){
		assert("true".toJson().toJsonString(true) == "true")
	}

	@Test
	fun `empty array`(){
		assert("[]".toJson().toJsonString(true) == "[\n\n]")
	}

	@Test
	fun `empty object`(){
		assert("{}".toJson().toJsonString(true) == "{\n\n}")
	}

	@Test
	fun `array 1 element`(){
		assert("[true]".
			toJson().toJsonString(true)
			== "[\n\ttrue\n]")
	}

	@Test
	fun `array 2 elements`(){
		assert("[true,false]".
			toJson().toJsonString(true)
			== "[\n\ttrue,\n\tfalse\n]")
	}

	@Test
	fun `object 1 element`(){
		val key1 = "\"a\""
		assert("{\"a\":true}".
			toJson().toJsonString(true)
			== "{\n\t$key1: true\n}")
	}

	@Test
	fun `object 2 elements`(){
		val key1 = "\"a\""
		val key2 = "\"b\""
		assert("{$key1:true,$key2:false}".
			toJson().toJsonString(true)
			== "{\n\t$key1: true,\n\t$key2: false\n}")
	}
}