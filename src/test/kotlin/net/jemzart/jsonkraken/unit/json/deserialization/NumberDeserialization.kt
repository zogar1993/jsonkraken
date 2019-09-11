package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class NumberDeserialization {
	@Test
	fun zero() {
		val json = JSONKraken.deserialize<JsonNumber>("0")
		assertEquals(0, json.cast<Int>())
	}

	@Test
	fun `minus zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("-0")
		assertEquals(0, json.cast<Int>())
	}

	@Test
	fun `one digit`() {
		val json = JSONKraken.deserialize<JsonNumber>("2")
		assertEquals(2, json.cast<Int>())
	}

	@Test
	fun `two digits`() {
		val json = JSONKraken.deserialize<JsonNumber>("42")
		assertEquals(42, json.cast<Int>())
	}

	@Test
	fun `one digit negative`() {
		val json = JSONKraken.deserialize<JsonNumber>("-2")
		assertEquals(-2, json.cast<Int>())
	}

	@Test
	fun `two digits negative`() {
		val json = JSONKraken.deserialize<JsonNumber>("-42")
		assertEquals(-42, json.cast<Int>())
	}

	@Test
	fun `zero point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("0.0")
		assertEquals(0, json.cast<Int>())
	}

	@Test
	fun `minus zero point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("-0.0")
		assertEquals(0, json.cast<Int>())
	}

	@Test
	fun `one digit point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("2.0")
		assertEquals(2, json.cast<Int>())
	}

	@Test
	fun `two digits point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("42.0")
		assertEquals(42, json.cast<Int>())
	}

	@Test
	fun `one digit negative point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("-2.0")
		assertEquals(-2, json.cast<Int>())
	}

	@Test
	fun `two digits negative point zero`() {
		val json = JSONKraken.deserialize<JsonNumber>("-42.0")
		assertEquals(-42, json.cast<Int>())
	}

	@Test
	fun `zero decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("0.5")
		assertEquals(0.5, json.cast(), 0.1)
	}

	@Test
	fun `minus zero decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("-0.5")
		assertEquals(-0.5, json.cast(), 0.1)
	}

	@Test
	fun `one digit decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("2.5")
		assertEquals(2.5, json.cast(), 0.1)
	}

	@Test
	fun `two digits decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("42.5")
		assertEquals(42.5, json.cast(), 0.1)
	}

	@Test
	fun `one digit negative decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("-2.5")
		assertEquals(-2.5, json.cast(), 0.1)
	}

	@Test
	fun `two digits negative decimal`() {
		val json = JSONKraken.deserialize<JsonNumber>("-42.5")
		assertEquals(-42.5, json.cast(), 0.1)
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting`() {
		JSONKraken.deserialize<JsonNumber>("01")
	}

	@Test(expected = TokenExpectationException::class)
	fun `zero starting negative`() {
		JSONKraken.deserialize<JsonNumber>("-01")
	}

	@Test
	fun `with e`() {
		JSONKraken.deserialize<JsonNumber>("123e65")
	}

	@Test
	fun `with E`() {
		JSONKraken.deserialize<JsonNumber>("1E22")
	}

	@Test
	fun `with E-`() {
		JSONKraken.deserialize<JsonNumber>("1E-2")
	}

	@Test
	fun `with E+`() {
		JSONKraken.deserialize<JsonNumber>("1E+2")
	}

	@Test
	fun `with e+`() {
		JSONKraken.deserialize<JsonNumber>("0e+1")
	}

	@Test
	fun `with e-`() {
		JSONKraken.deserialize<JsonNumber>("0e-1")
	}

	@Test
	fun `fraction and exponent`() {
		JSONKraken.deserialize<JsonNumber>("123.456e78")
	}

	@Test
	fun `after space`() {
		JSONKraken.deserialize<JsonNumber>(" 4")
	}

	@Test
	fun `close to zero`() {
		JSONKraken.deserialize<JsonNumber>("-0.000000000000000000000000000000000000000000000000000000000000000000000000000001")
	}

	@Test
	fun `extremely high number`() {
		val json = JSONKraken.deserialize<JsonNumber>("1.0e+28")
		assertEquals(1.0E28, json.cast(), 0.1)
	}

	@Test
	fun `extremely low number`() {
		val json = JSONKraken.deserialize<JsonNumber>("-1.0e+28")
		assertEquals(-1.0E28, json.cast(), 0.1)
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonNumber>("0.")
	}
}