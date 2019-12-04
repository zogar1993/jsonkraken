package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.exceptions.NoSuchPropertyException
import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonObject

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

	@Test(expected = NoSuchPropertyException::class)
	fun `getting a non existent property when fetching by string fails`() {
		val obj = JsonObject()
		obj["this_property_does_not_exist"]
	}

	@Test(expected = NoSuchPropertyException::class)
	fun `getting a non existent property when fetching by int fails`() {
		val obj = JsonObject()
		obj[-1]
	}
}