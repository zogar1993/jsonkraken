package net.jemzart.jsonkraken.unit.helpers.any

import net.jemzart.jsonkraken.helpers.isValidJsonType
import net.jemzart.jsonkraken.values.JsonArray
import org.junit.Test

class IsValidJsonType {

	@Test
	fun byte(){
		val byte: Byte = Byte.MIN_VALUE
		assert(byte.isValidJsonType())
	}

	@Test
	fun short(){
		val short: Short = Short.MIN_VALUE
		assert(short.isValidJsonType())
	}

	@Test
	fun int(){
		val int: Int = Int.MIN_VALUE
		assert(int.isValidJsonType())
	}

	@Test
	fun long(){
		val long: Long = Long.MIN_VALUE
		assert(long.isValidJsonType())
	}

	@Test
	fun float(){
		val float: Float = Float.MIN_VALUE
		assert(float.isValidJsonType())
	}

	@Test
	fun double(){
		val double: Double = Double.MIN_VALUE
		assert(double.isValidJsonType())
	}

	@Test
	fun string(){
		assert("string".isValidJsonType())
	}

	@Test
	fun boolean(){
		assert(true.isValidJsonType())
	}

	@Test
	fun jsonArray(){
		assert(JsonArray().isValidJsonType())
	}

	@Test
	fun jsonObject(){
		val int: Int = Int.MIN_VALUE
		assert(int.isValidJsonType())
	}

	@Test
	fun exceptionIsNotValid(){
		assert(!Exception().isValidJsonType())
	}
}