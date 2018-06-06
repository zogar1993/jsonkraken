package jemzart.jsonkraken.unit.json.`object`

import jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectExists {
	@Test
	fun exists(){
		val obj = JsonObject()

		obj["0"] = ""

		assert(obj.exists("0"))
		assert(obj.exists(0))
	}

	@Test
	fun doesNotExist(){
		val obj = JsonObject()

		assert(!obj.exists("0"))
		assert(!obj.exists(0))
	}
}