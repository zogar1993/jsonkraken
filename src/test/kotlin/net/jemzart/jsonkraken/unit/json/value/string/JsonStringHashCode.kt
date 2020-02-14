package net.jemzart.jsonkraken.unit.json.value.string

import net.jemzart.jsonkraken.JsonString
import org.junit.Assert.assertEquals

import org.junit.Test

class JsonStringHashCode {
	@Test
	fun simple() {
		val number = JsonString("A")
		assertEquals("A".hashCode(), number.hashCode())
	}
}