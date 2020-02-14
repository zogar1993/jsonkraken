package net.jemzart.jsonkraken.unit.json.value.boolean

import net.jemzart.jsonkraken.JsonFalse
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonBooleanToString {
	@Test
	fun `true`() {
		val boolean = JsonTrue
		val string = boolean.toString()
		val serialized = JsonKraken.serialize(boolean)
		assertEquals(serialized, string)
	}

	@Test
	fun `false`() {
		val boolean = JsonFalse
		val string = boolean.toString()
		val serialized = JsonKraken.serialize(boolean)
		assertEquals(serialized, string)
	}
}