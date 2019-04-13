import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.jsonDeserialize
import net.jemzart.jsonkraken.values.JsonFalse
import org.junit.Test

class FalseDeserialization {
	@Test
	fun `false`() {
		val json = "false".jsonDeserialize()
		assert(json == JsonFalse)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled false`() {
		"fffff".jsonDeserialize()
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		"fals".jsonDeserialize()
	}
}