package jemzart.jsonkraken.unit

import jemzart.jsonkraken.JSON_VALUE
import jemzart.jsonkraken.values.JsonArray
import jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class ArrayItemGetting{
    private val insertion = JsonObject()

	@Test
    fun byInt(){
        val arr = JsonArray()
	    arr.add(insertion)

	    assert(arr[0, JSON_VALUE] == insertion)
	    assert(arr[0] == insertion)
    }

	@Test
	fun byString(){
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr["0", JSON_VALUE] == insertion)
		assert(arr["0"] == insertion)
	}

	@Test
	fun negativeNotation(){
		val arr = JsonArray()
		arr.add(JsonArray())
		arr.add(insertion)

		assert(arr[-1, JSON_VALUE] == insertion)
		assert(arr[-1] == insertion)
	}
}