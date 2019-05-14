package net.jemzart.jsonkraken.unit.json.value.`object`

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectSetOperator {
	private val insertion: JsonContainer = JsonObject()

	@Test
	fun `by String`() {
		val obj = JsonObject()
		obj["0"] = insertion
		assert(obj["0"] == insertion)
	}

	@Test
	fun `by Int`() {
		val obj = JsonObject()
		obj[0] = insertion
		assert(obj["0"] == insertion)
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`() {
		val obj = JsonObject()
		obj["0"] = Exception()
	}

	@Test
	fun `circular reference not allowed`() {
		val obj = JsonObject()
		val arr = JsonArray(obj)

		runCatching { obj["0"] = arr }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == obj)
			assert(it.intruder == arr)
		}
	}

	@Test
	fun `self reference not allowed`() {
		val obj = JsonObject()

		runCatching { obj["0"] = obj }.onSuccess { assert(false) }.onFailure {
			it as CircularReferenceException
			assert(it.host == obj)
			assert(it.intruder == obj)
		}
	}

	@Test
	fun `json string compliance for keys`() {
		JsonStringCompliance.verify { value: Any -> JsonObject()[value.toString()] = null }
	}

	@Test
	fun `json string compliance for values`() {
		JsonStringCompliance.verify { value: Any -> JsonObject()["0"] = value }
	}
}