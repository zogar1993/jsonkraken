package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Test

class NumberDeserialization {
	@Test
	fun zero() {
		val json = "0".jsonDeserialize()
		assert(json == JsonNumber(0.0))
	}

	@Test
	fun `minus zero`() {
		val json = "-0".jsonDeserialize()
		assert(json == JsonNumber(0.0))
	}

	@Test
	fun `one digit`() {
		val json = "2".jsonDeserialize()
		assert(json == JsonNumber(2.0))
	}

	@Test
	fun `two digits`() {
		val json = "42".jsonDeserialize()
		assert(json == JsonNumber(42.0))
	}

	@Test
	fun `one digit negative`() {
		val json = "-2".jsonDeserialize()
		assert(json == JsonNumber(-2.0))
	}

	@Test
	fun `two digits negative`() {
		val json = "-42".jsonDeserialize()
		assert(json == JsonNumber(-42.0))
	}

	@Test
	fun `zero point zero`() {
		val json = "0.0".jsonDeserialize()
		assert(json == JsonNumber(0.0))
	}

	@Test
	fun `minus zero point zero`() {
		val json = "-0.0".jsonDeserialize()
		assert(json == JsonNumber(0.0))
	}

	@Test
	fun `one digit point zero`() {
		val json = "2.0".jsonDeserialize()
		assert(json == JsonNumber(2.0))
	}

	@Test
	fun `two digits point zero`() {
		val json = "42.0".jsonDeserialize()
		assert(json == JsonNumber(42.0))
	}

	@Test
	fun `one digit negative point zero`() {
		val json = "-2.0".jsonDeserialize()
		assert(json == JsonNumber(-2.0))
	}

	@Test
	fun `two digits negative point zero`() {
		val json = "-42.0".jsonDeserialize()
		assert(json == JsonNumber(-42.0))
	}

	@Test
	fun `zero decimal`() {
		val json = "0.5".jsonDeserialize()
		assert(json == JsonNumber(0.5))
	}

	@Test
	fun `minus zero decimal`() {
		val json = "-0.5".jsonDeserialize()
		assert(json == JsonNumber(-0.5))
	}

	@Test
	fun `one digit decimal`() {
		val json = "2.5".jsonDeserialize()
		assert(json == JsonNumber(2.5))
	}

	@Test
	fun `two digits decimal`() {
		val json = "42.5".jsonDeserialize()
		assert(json == JsonNumber(42.5))
	}

	@Test
	fun `one digit negative decimal`() {
		val json = "-2.5".jsonDeserialize()
		assert(json == JsonNumber(-2.5))
	}

	@Test
	fun `two digits negative decimal`() {
		val json = "-42.5".jsonDeserialize()
		assert(json == JsonNumber(-42.5))
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting`() {
		"01".jsonDeserialize()
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting negative`() {
		"-01".jsonDeserialize()
	}

	@Test
	fun `with e`() {
		"123e65".jsonDeserialize()
	}

	@Test
	fun `with E`() {
		"1E22".jsonDeserialize()
	}

	@Test
	fun `with E-`() {
		"1E-2".jsonDeserialize()
	}

	@Test
	fun `with E+`() {
		"1E+2".jsonDeserialize()
	}

	@Test
	fun `with e+`() {
		"0e+1".jsonDeserialize()
	}

	@Test
	fun `with e-`() {
		"0e-1".jsonDeserialize()
	}

	@Test
	fun `fraction and exponent`() {
		"123.456e78".jsonDeserialize()
	}

	@Test
	fun `after space`() {
		" 4".jsonDeserialize()
	}

	@Test
	fun `close to zero`() {
		"-0.000000000000000000000000000000000000000000000000000000000000000000000000000001".jsonDeserialize()
	}

	@Test
	fun `extremely high number`() {
		assert("1.0e+28".jsonDeserialize() == JsonNumber(1.0E28))
	}

	@Test
	fun `extremely low number`() {
		assert("-1.0e+28".jsonDeserialize() == JsonNumber(-1.0E28))
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"0.".jsonDeserialize()
	}
}