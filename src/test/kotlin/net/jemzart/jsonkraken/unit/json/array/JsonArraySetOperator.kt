package net.jemzart.jsonkraken.unit.json.array

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidJsonTypeException
import net.jemzart.jsonkraken.helpers.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class JsonArraySetOperator{
    private val insertion = JsonArray()
    @Test
    fun `existing index by Int`(){
        val arr = JsonArray()
        arr.add(null)

        arr[0] = insertion

        assert(arr[0] == insertion)
    }

    @Test
    fun `existing index by String`(){
        val arr = JsonArray()
        arr.add(null)

        arr["0"] = insertion

        assert(arr[0] == insertion)
    }

    @Test
    fun `non existing index by Int`(){
        val arr = JsonArray()
        arr[0] = insertion
        assert(arr[0] == insertion)
    }

    @Test
    fun `non existing index by String`(){
        val arr = JsonArray()
        arr["0"] = insertion
        assert(arr[0] == insertion)
    }

	@Test
	fun `non existing index grows until index`(){
		val arr = JsonArray()
		arr[6] = insertion
		assert(arr.size == 7)
	}

	@Test
	fun `reverse notation`(){
		val arr = JsonArray()
		arr.add(null)
		arr.add(null)
		arr.add(null)

		arr[-2] = insertion

		assert(arr[1] == insertion)
	}

	@Test(expected = InvalidJsonTypeException::class)
	fun `fails on invalid type`(){
		val arr = JsonArray()
		arr["0"] = Exception()
	}

	@Test(expected = CircularReferenceException::class)
	fun `circular reference not allowed`(){
		val arr = JsonArray()
		val obj = JsonObject("0" to arr)

		arr[0] = obj
	}

	@Test(expected = CircularReferenceException::class)
	fun `self reference not allowed`(){
		val arr = JsonArray()

		arr[0] = arr
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonArray()[0] = value }
	}
}