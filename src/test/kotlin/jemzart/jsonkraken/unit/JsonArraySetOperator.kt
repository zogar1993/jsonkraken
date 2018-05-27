package jemzart.jsonkraken.unit

import jemzart.jsonkraken.ANY
import jemzart.jsonkraken.JSON_OBJECT
import jemzart.jsonkraken.JSON_VALUE
import jemzart.jsonkraken.STRING
import jemzart.jsonkraken.values.JsonArray
import jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArraySetOperator{
    private val insertion = JSON_VALUE
    @Test
    fun existingIndexByInt(){
        val arr = JsonArray()
        arr.add(null)

        arr[0] = insertion

        assert(arr[0] == insertion)
    }

    @Test
    fun existingIndexByString(){
        val arr = JsonArray()
        arr.add(null)

        arr["0"] = insertion

        assert(arr[0] == insertion)
    }

    @Test
    fun nonExistingIndexByInt(){
        val arr = JsonArray()
        arr[0] = insertion
        assert(arr[0] == insertion)
    }

    @Test
    fun nonExistingIndexByString(){
        val arr = JsonArray()
        arr["0"] = insertion
        assert(arr[0] == insertion)
    }

	@Test
	fun nonExistingIndexGrowsUntilIndex(){
		val arr = JsonArray()
		arr[6] = insertion
		assert(arr.size == 7)
	}

	@Test
	fun negativeNotation(){
		val arr = JsonArray()
		arr.add(null)
		arr.add(null)
		arr.add(null)

		arr[-2] = insertion

		assert(arr[1, JSON_VALUE] == insertion)
		assert(arr[1] == insertion)
	}
}