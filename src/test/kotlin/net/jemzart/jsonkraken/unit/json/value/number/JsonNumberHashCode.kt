package net.jemzart.jsonkraken.unit.json.value.number

import net.jemzart.jsonkraken.JsonNumber
import org.junit.Assert.assertEquals

import org.junit.Test

class JsonNumberHashCode {
	@Test
	fun simple() {
		val number = JsonNumber("1")
		assertEquals("1".hashCode(), number.hashCode())
	}
}