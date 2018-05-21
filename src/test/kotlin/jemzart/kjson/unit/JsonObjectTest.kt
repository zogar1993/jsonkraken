package jemzart.kjson.unit

import jemzart.kjson.STRING
import jemzart.kjson.values.JsonArray
import jemzart.kjson.values.JsonObject
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = JsonObject()
		obj["name"] = "Ragoz"
		assert(obj["name", STRING] == "Ragoz")
	}

	@Test
	fun simpleArray() {
		val arr = JsonArray()
		arr.add("Ragoz")
		assert(arr[0, STRING] == "Ragoz")
	}


	@Test
	fun insertFirst() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(0, "Joelin")
		assert(obj[0, STRING] == "Joelin")
		assert(obj[1, STRING] == "Von Chap")
		assert(obj[2, STRING] == "Ulf")
	}

	@Test
	fun insertInTheMiddle() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(1, "Joelin")
		assert(obj[1, STRING] == "Joelin")
		assert(obj[0, STRING] == "Von Chap")
		assert(obj[2, STRING] == "Ulf")
	}

	@Test
	fun insertLast() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(2, "Joelin")
		assert(obj[2, STRING] == "Joelin")
		assert(obj[0, STRING] == "Von Chap")
		assert(obj[1, STRING] == "Ulf")
	}
}