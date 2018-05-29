package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.INTEGER
import jemzart.jsonkraken.toJson
import jemzart.jsonkraken.values.JsonNonCollection
import org.junit.Test

class IntegerDeserialization{


	@Test
	fun zero() {
		val json = "[0]".toJson()
		assert(json[0, INTEGER] == 0)
	}

	@Test
	fun minusZero() {
		val json = "[-0]".toJson()
		assert(json[0, INTEGER] == 0)
	}

	@Test
	fun oneDigit() {
		val json = "[2]".toJson()
		assert(json[0, INTEGER] == 2)
	}

	@Test
	fun twoDigits() {
		val json = "[42]".toJson()
		assert(json[0, INTEGER] == 42)
	}

	@Test
	fun oneDigitNegative() {
		val json = "[-2]".toJson()
		assert(json[0, INTEGER] == -2)
	}

	@Test
	fun twoDigitsNegative() {
		val json = "[-42]".toJson()
		assert(json[0, INTEGER] == -42)
	}

	@Test(expected = Throwable::class)
	fun zeroStarting() {
		"[01]".toJson()
	}

	@Test(expected = Throwable::class)
	fun zeroStartingNegative() {
		"[-01]".toJson()
	}
}