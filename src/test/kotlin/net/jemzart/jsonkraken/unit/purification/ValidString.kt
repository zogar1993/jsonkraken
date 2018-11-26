package net.jemzart.jsonkraken.unit.purification

import net.jemzart.jsonkraken.exceptions.InvalidJsonStringException
import net.jemzart.jsonkraken.helpers.validate
import org.junit.Test

class ValidString {
	@Test
	fun empty() = "".validate()

	@Test
	fun word() = "kraken".validate()

	@Test
	fun `escaped r`() = "\\r".validate()

	@Test
	fun `escaped n`() = "\\n".validate()

	@Test
	fun `escaped t`() = "\\t".validate()

	@Test
	fun `escaped uFFFF`() = "\\uAF09".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `escaped uFFF`() = "\\u000".validate()

	@Test
	fun `escaped double quotes`() = "\\\"".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `unescaped double quotes`() = "\"".validate()

	@Test
	fun `escaped reverse solidus`() = "\\\\".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `unescaped reverse solidus`() = "\\".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `unescaped tab`() = "\t".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `unescaped carriage return`() = "\r".validate()

	@Test(expected = InvalidJsonStringException::class)
	fun `unescaped newline`() = "\n".validate()
}