package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArraySetOperator{
    private val insertion = JsonArray()
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

		assert(arr[1] == insertion)
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun failsOnInvalidType(){
		val arr = JsonArray()
		arr["0"] = Exception()
	}

	@Test(expected = CircularReferenceException::class)
	fun circularReferenceNotAllowed(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		arr[0] = obj
	}

	@Test(expected = CircularReferenceException::class)
	fun selfReferenceNotAllowed(){
		val arr = JsonArray()

		arr[0] = arr
	}
}