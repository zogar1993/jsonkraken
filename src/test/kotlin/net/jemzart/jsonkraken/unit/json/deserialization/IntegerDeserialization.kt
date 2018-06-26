package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.toJson
import org.junit.Test

class IntegerDeserialization{


	@Test
	fun zero() {
		val json = "0".toJson()
		assert(json == 0)
	}

	@Test
	fun minusZero() {
		val json = "-0".toJson()
		assert(json == 0)
	}

	@Test
	fun oneDigit() {
		val json = "2".toJson()
		assert(json == 2)
	}

	@Test
	fun twoDigits() {
		val json = "42".toJson()
		assert(json == 42)
	}

	@Test
	fun oneDigitNegative() {
		val json = "-2".toJson()
		assert(json == -2)
	}

	@Test
	fun twoDigitsNegative() {
		val json = "-42".toJson()
		assert(json == -42)
	}

	@Test(expected = Throwable::class)
	fun zeroStarting() {
		"01".toJson()
	}

	@Test(expected = Throwable::class)
	fun zeroStartingNegative() {
		"-01".toJson()
	}
}