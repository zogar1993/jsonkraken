package jemzart.kjson.unit

import jemzart.kjson.STRING
import jemzart.kjson.jsonArray
import jemzart.kjson.jsonObject
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = jsonObject()
		obj.add("name", "Ragoz")
		assert(obj["name", STRING] == "Ragoz")
	}

	@Test
	fun simpleArray() {
		val arr = jsonArray()
		arr.add("Ragoz")
		assert(arr[0, STRING] == "Ragoz")
	}
}