package net.jemzart.jsonkraken.unit.json.value.boolean


import net.jemzart.jsonkraken.JsonFalse
import net.jemzart.jsonkraken.JsonTrue
import org.junit.Test

class JsonBooleanCast {
	@Test
	fun `JsonTrue as boolean`() {
		val value = JsonTrue
		assert(value.cast<Boolean>() == true)
	}

	@Test
	fun `JsonFalse as boolean`() {
		val value = JsonFalse
		assert(value.cast<Boolean>() == false)
	}

	@Test
	fun `casting to Any returns true`() {
		val json = JsonTrue
		val result = json.cast<Any>()

		assert(true == result)
	}

	@Test
	fun `casting to Any returns false`() {
		val json = JsonFalse
		val result = json.cast<Any>()

		assert(false == result)
	}
}