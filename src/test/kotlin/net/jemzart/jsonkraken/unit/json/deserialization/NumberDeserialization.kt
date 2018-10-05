package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class NumberDeserialization{


	@Test
	fun zero() {
		val json = "0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun minusZero() {
		val json = "-0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun oneDigit() {
		val json = "2".toJson()
		assert(json == 2.0)
	}

	@Test
	fun twoDigits() {
		val json = "42".toJson()
		assert(json == 42.0)
	}

	@Test
	fun oneDigitNegative() {
		val json = "-2".toJson()
		assert(json == -2.0)
	}

	@Test
	fun twoDigitsNegative() {
		val json = "-42".toJson()
		assert(json == -42.0)
	}

	@Test
	fun zeroPointZero() {
		val json = "0.0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun minusZeroPointZero() {
		val json = "-0.0".toJson()
		assert(json == 0.0)
	}

	@Test
	fun oneDigitPointZero() {
		val json = "2.0".toJson()
		assert(json == 2.0)
	}

	@Test
	fun twoDigitsPointZero() {
		val json = "42.0".toJson()
		assert(json == 42.0)
	}

	@Test
	fun oneDigitNegativePointZero() {
		val json = "-2.0".toJson()
		assert(json == -2.0)
	}

	@Test
	fun twoDigitsNegativePointZero() {
		val json = "-42.0".toJson()
		assert(json == -42.0)
	}
	@Test
	fun zeroDecimal() {
		val json = "0.5".toJson()
		assert(json == 0.5)
	}

	@Test
	fun minusZeroDecimal() {
		val json = "-0.5".toJson()
		assert(json == -0.5)
	}

	@Test
	fun oneDigitDecimal() {
		val json = "2.5".toJson()
		assert(json == 2.5)
	}

	@Test
	fun twoDigitsDecimal() {
		val json = "42.5".toJson()
		assert(json == 42.5)
	}

	@Test
	fun oneDigitNegativeDecimal() {
		val json = "-2.5".toJson()
		assert(json == -2.5)
	}

	@Test
	fun twoDigitsNegativeDecimal() {
		val json = "-42.5".toJson()
		assert(json == -42.5)
	}

	@Test(expected = TokenExpectationException::class)
	fun zeroStarting() {
		"01".toJson()
	}

	@Test(expected = TokenExpectationException::class)
	fun zeroStartingNegative() {
		"-01".toJson()
	}
}