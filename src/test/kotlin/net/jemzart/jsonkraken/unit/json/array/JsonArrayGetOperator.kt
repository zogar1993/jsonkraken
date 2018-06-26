package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class JsonArrayGetOperator{
    private val insertion = JsonArray()

	@Test
    fun byInt(){
        val arr = JsonArray()
	    arr.add(insertion)

	    assert(arr[0] == insertion)
    }

	@Test
	fun byString(){
		val arr = JsonArray()
		arr.add(insertion)

		assert(arr["0"] == insertion)
	}

	@Test
	fun negativeNotation(){
		val arr = JsonArray()
		arr.add(null)
		arr.add(insertion)

		assert(arr[-1] == insertion)
	}
}