package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test


class JsonArrayConstruction {
	@Test
	fun empty(){
		val arr = JsonArray()

		assert(arr.size == 0)
	}

	@Test
	fun oneProperty(){
		val arr = JsonArray("Von Chap")

		assert(arr[0] == "Von Chap")
	}

	@Test
	fun twoProperties(){
		val arr = JsonArray("Von Chap", "Joelin")

		assert(arr[0] == "Von Chap")
		assert(arr[1] == "Joelin")
	}
}