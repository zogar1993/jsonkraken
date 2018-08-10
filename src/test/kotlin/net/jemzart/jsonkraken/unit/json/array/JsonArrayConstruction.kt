package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test


class JsonArrayConstruction {
	@Test
	fun empty(){
		val obj = JsonArray()

		assert(obj.size == 0)
	}

	@Test
	fun oneProperty(){
		val obj = JsonArray("Von Chap")

		assert(obj[0] == "Von Chap")
	}

	@Test
	fun twoProperties(){
		val obj = JsonArray("Von Chap", "Joelin")

		assert(obj[0] == "Von Chap")
		assert(obj[1] == "Joelin")
	}
}