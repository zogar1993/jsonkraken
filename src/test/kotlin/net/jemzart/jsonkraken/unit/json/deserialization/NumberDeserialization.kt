package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class NumberDeserialization{
	@Test
	fun zero(){
		val json = "0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun `minus zero`(){
		val json = "-0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun `one digit`(){
		val json = "2".toJson()
		assert(json == 2.0)
	}

	@Test
	fun `two digits`(){
		val json = "42".toJson()
		assert(json == 42.0)
	}

	@Test
	fun `one digit negative`(){
		val json = "-2".toJson()
		assert(json == -2.0)
	}

	@Test
	fun `two digits negative`(){
		val json = "-42".toJson()
		assert(json == -42.0)
	}

	@Test
	fun `zero point zero`() {
		val json = "0.0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun `minus zero point zero`(){
		val json = "-0.0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun `one digit point zero`(){
		val json = "2.0".toJson()
		assert(json == 2.0)
	}

	@Test
	fun `two digits point zero`(){
		val json = "42.0".toJson()
		assert(json == 42.0)
	}

	@Test
	fun `one digit negative point zero`(){
		val json = "-2.0".toJson()
		assert(json == -2.0)
	}

	@Test
	fun `two digits negative point zero`(){
		val json = "-42.0".toJson()
		assert(json == -42.0)
	}
	@Test
	fun `zero decimal`(){
		val json = "0.5".toJson()
		assert(json == 0.5)
	}

	@Test
	fun `minus zero decimal`(){
		val json = "-0.5".toJson()
		assert(json == -0.5)
	}

	@Test
	fun `one digit decimal`(){
		val json = "2.5".toJson()
		assert(json == 2.5)
	}

	@Test
	fun `two digits decimal`(){
		val json = "42.5".toJson()
		assert(json == 42.5)
	}

	@Test
	fun `one digit negative decimal`(){
		val json = "-2.5".toJson()
		assert(json == -2.5)
	}

	@Test
	fun `two digits negative decimal`(){
		val json = "-42.5".toJson()
		assert(json == -42.5)
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting`(){
		"01".toJson()
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting negative`(){
		"-01".toJson()
	}
}