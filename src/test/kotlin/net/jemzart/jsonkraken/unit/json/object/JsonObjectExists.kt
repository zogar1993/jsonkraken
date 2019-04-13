package net.jemzart.jsonkraken.unit.json.`object`

import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectExists {
	@Test
	fun exists() {
		val obj = JsonObject()

		obj["0"] = ""

		assert(obj.exists("0"))
	}

	@Test
	fun `does not exist`() {
		val obj = JsonObject()

		assert(!obj.exists("0"))
	}
}