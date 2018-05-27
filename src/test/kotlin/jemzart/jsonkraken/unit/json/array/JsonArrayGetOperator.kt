package jemzart.jsonkraken.unit.json.array

import jemzart.jsonkraken.JSON_VALUE
import jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayGetOperator{
    private val insertion = JSON_VALUE

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
		arr.add(null)
		arr.add(insertion)

		assert(arr[-1, JSON_VALUE] == insertion)
		assert(arr[-1] == insertion)
	}
}