package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.JsonContainer
import net.jemzart.jsonkraken.JsonObject
import net.jemzart.jsonkraken.exceptions.NoSuchPropertyException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonObjectGetOperator {
	private val insertion: JsonContainer = JsonObject()

	@Test
	fun `by String`() {
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj["0"] == insertion)
		assert(obj["0"] == insertion)
	}

	@Test
	fun `by Int`() {
		val obj = JsonObject()

		obj["0"] = insertion

		assert(obj[0] == insertion)
		assert(obj[0] == insertion)
	}

	@Test
	fun `getting a non existent property when fetching by string fails`() {
		val obj = JsonObject()
		runCatching { obj["this_property_does_not_exist"] }.onSuccess { Assert.fail() }.onFailure { e ->
			assertTrue(e is NoSuchPropertyException)
			e as NoSuchPropertyException
			assertEquals(obj, e.obj)
			assertEquals("this_property_does_not_exist", e.property)
		}
	}

	@Test(expected = NoSuchPropertyException::class)
	fun `getting a non existent property when fetching by int fails`() {
		val obj = JsonObject()
		obj[-1]
	}
}