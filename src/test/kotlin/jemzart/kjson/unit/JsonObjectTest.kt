package jemzart.kjson.unit

import jemzart.kjson.STRING
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
}