package jemzart.kjson.unit

import jemzart.kjson.jsonArray
import jemzart.kjson.jsonObject
import jemzart.kjson.jsonString
import jemzart.kjson.values.JsonString
import org.junit.Test

class JsonObjectTest {
	@Test
	fun added(){
		val obj = jsonObject()
		obj.add("name", jsonString("Ragoz"))
		assert(obj["name"].value == "Ragoz")
	}

	@Test
	fun simpleArray() {
		val arr = jsonArray()
		arr.add(JsonString("Ragoz"))
		assert(arr[0].value == "Ragoz")
	}
}