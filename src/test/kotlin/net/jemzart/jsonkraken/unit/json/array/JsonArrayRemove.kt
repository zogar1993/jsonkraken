package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayRemove{
	@Test
	fun `by Int`(){
		val arr = JsonArray()
		arr.add(null)

		arr.remove(0)

		assert(arr.size == 0)
	}

	@Test
	fun `by String`(){
		val arr = JsonArray()
		arr.add(null)

		arr.remove("0")

		assert(arr.size == 0)
	}
}