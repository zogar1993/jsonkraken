package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class NullDeserialization{
	@Test
	fun `null`(){
		val json = "null".toJson()
		assert(json == null)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled null`(){
		"nnnn".toJson()
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`(){
		"nul".toJson()
	}
}