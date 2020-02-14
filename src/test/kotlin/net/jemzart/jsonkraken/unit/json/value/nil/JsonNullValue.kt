package net.jemzart.jsonkraken.unit.json.value.nil

import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.JsonNull

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class JsonNullValue {
	@Test
	fun simple() {
		assertNull(JsonNull.value)
	}
}