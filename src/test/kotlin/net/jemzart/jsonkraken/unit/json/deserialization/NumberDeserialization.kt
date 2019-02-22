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

	@Test
	fun `with e`(){
		"123e65".toJson()
	}

	@Test
	fun `with E`(){
		"1E22".toJson()
	}

	@Test
	fun `with E-`(){
		"1E-2".toJson()
	}

	@Test
	fun `with E+`(){
		"1E+2".toJson()
	}

	@Test
	fun `with e+`(){
		"0e+1".toJson()
	}

	@Test
	fun `with e-`(){
		"0e-1".toJson()
	}

	@Test
	fun `fraction and exponent`(){
		"123.456e78".toJson()
	}

	@Test
	fun `after space`(){
		" 4".toJson()
	}

	@Test
	fun `close to zero`(){
		"-0.000000000000000000000000000000000000000000000000000000000000000000000000000001".toJson()
	}

	@Test
	fun `extremely high number`(){
		assert("1.0e+28".toJson() == 1.0E28)
	}

	@Test
	fun `extremely low number`(){
		assert("-1.0e+28".toJson() == -1.0E28)
	}

	@Test(expected = TokenExpectationException::class)
	fun `unexpected EOF`(){
		"0.".toJson()
	}
}