package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = JsonObject()
		obj["name"] = "Ragoz"
		assert(obj["name"] == "Ragoz")
	}

	@Test
	fun `simple array`() {
		val arr = JsonArray()
		arr.add("Ragoz")
		assert(arr[0] == "Ragoz")
	}


	@Test
	fun `insert first`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(0, "Joelin")
		assert(obj[0] == "Joelin")
		assert(obj[1] == "Von Chap")
		assert(obj[2] == "Ulf")
	}

	@Test
	fun `insert in the middle`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(1, "Joelin")
		assert(obj[1] == "Joelin")
		assert(obj[0] == "Von Chap")
		assert(obj[2] == "Ulf")
	}

	@Test
	fun `insert last`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(2, "Joelin")
		assert(obj[2] == "Joelin")
		assert(obj[0] == "Von Chap")
		assert(obj[1] == "Ulf")
	}
}