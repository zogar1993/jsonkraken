package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayExists {
	@Test
	fun exists(){
		val arr = JsonArray()

		arr[0] = ""

		assert(arr.exists("0"))
		assert(arr.exists(0))
	}

	@Test
	fun doesNotExist(){
		val arr = JsonArray()

		assert(!arr.exists("0"))
		assert(!arr.exists(0))
	}
}