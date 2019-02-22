package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class TrueDeserialization{
	@Test
	fun `true`(){
		val json = "true".toJson()
		assert(json == true)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled true`(){
		"tttt".toJson()
	}

	@Test(expected = TokenExpectationException::class)
	fun `unexpected EOF`(){
		"tru".toJson()
	}
}