package net.jemzart.jsonkraken.unit.json.string

import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test

class StringValidate {
	@Test
	fun `json string compliance`() {
		JsonStringCompliance.verify { value: Any -> JsonString(value.toString()) }
	}

	@Test
	fun `empty string`() {
		val value = JsonString("")
		assert(value.cast<String>() == "")
	}

	@Test
	fun `one word`() {
		val value = JsonString("kraken")
		assert(value.cast<String>() == "kraken")
	}

	@Test
	fun `escaped r`() {
		val value = JsonString("\\r")
		assert(value.cast<String>() == "\\r")
	}

	@Test
	fun `escaped n`() {
		val value = JsonString("\\n")
		assert(value.cast<String>() == "\\n")
	}

	@Test
	fun `escaped t`() {
		val value = JsonString("\\t")
		assert(value.cast<String>() == "\\t")
	}

	@Test
	fun `escaped b`() {
		val value = JsonString("\\b")
		assert(value.cast<String>() == "\\b")
	}

	@Test
	fun `escaped f`() {
		val value = JsonString("\\f")
		assert(value.cast<String>() == "\\f")
	}

	@Test
	fun `escaped solidus`() {
		val value = JsonString("\\/")
		assert(value.cast<String>() == "\\/")
	}

	@Test
	fun `escaped double quotes`() {
		val value = JsonString("\\\"")
		assert(value.cast<String>() == "\\\"")
	}

	@Test
	fun `escaped reverse solidus`() {
		val value = JsonString("\\\\")
		assert(value.cast<String>() == "\\\\")
	}

	@Test
	fun unicode() {
		val value = JsonString("\\uAF09")
		assert(value.cast<String>() == "\\uAF09")
	}
}