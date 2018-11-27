package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.helpers.purify
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import org.junit.Test

class AnyPurify {
	@Test
	fun `negative zero to zero`(){
		assert((-0.0).purify() == 0.0)
	}

	@Test
	fun `Char to String`(){
		assert('s'.purify() == "s")
	}

	@Test
	fun `Int to Double`(){
		assert(13.purify() == 13.0)
	}

	@Test
	fun `null`(){
		assert(null.purify() == null)
	}

	@Test
	fun `true`(){
		assert(true.purify() == true)
	}

	@Test
	fun `false`(){
		assert(false.purify() == false)
	}

	@Test
	fun `self circularity`(){
		val arr = JsonArray()
		kotlin.runCatching { arr.purify(arr) }.
			onSuccess { assert(false) }.
			onFailure {
				it as CircularReferenceException
				assert(it.host == arr)
				assert(it.intruder == arr)
			}
	}

	@Test
	fun `A to B to A circularity`(){
		val obj = JsonObject()
		val arr = JsonArray(obj)
		kotlin.runCatching { arr.purify(obj) }.
			onSuccess { assert(false) }.
			onFailure {
				it as CircularReferenceException
				assert(it.host == obj)
				assert(it.intruder == arr)
			}
	}

	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> value.purify() }
	}
}