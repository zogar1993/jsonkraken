package jemzart.kjson.unit

import jemzart.kjson.STRING
import jemzart.kjson.after
import jemzart.kjson.values.JsonArray
import jemzart.kjson.values.JsonObject
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = JsonObject()
		obj.add("name", "Ragoz")
		assert(obj["name", STRING] == "Ragoz")
	}

	@Test
	fun simpleArray() {
		val arr = JsonArray()
		arr.add("Ragoz")
		assert(arr[0, STRING] == "Ragoz")
	}


	@Test
	fun insert() {
		val obj = JsonObject()
		obj.add("Captain", "Von Chap")
		obj.add("Soldier", "Ulf")
		obj.insert("Hero" to "Joelin" after "Captain")
		assert(obj[1, STRING] == "Ragoz")
	}

}