package com.jemzart.jsonkraken.unit

import com.jemzart.jsonkraken.values.JsonArray
import com.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = JsonObject()
		obj["name"] = "Ragoz"
		assert(obj["name"] == "Ragoz")
	}

	@Test
	fun simpleArray() {
		val arr = JsonArray()
		arr.add("Ragoz")
		assert(arr[0] == "Ragoz")
	}


	@Test
	fun insertFirst() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(0, "Joelin")
		assert(obj[0] == "Joelin")
		assert(obj[1] == "Von Chap")
		assert(obj[2] == "Ulf")
	}

	@Test
	fun insertInTheMiddle() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(1, "Joelin")
		assert(obj[1] == "Joelin")
		assert(obj[0] == "Von Chap")
		assert(obj[2] == "Ulf")
	}

	@Test
	fun insertLast() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(2, "Joelin")
		assert(obj[2] == "Joelin")
		assert(obj[0] == "Von Chap")
		assert(obj[1] == "Ulf")
	}
}