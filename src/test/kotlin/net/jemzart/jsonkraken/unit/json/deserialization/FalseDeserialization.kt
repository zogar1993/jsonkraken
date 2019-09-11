import net.jemzart.jsonkraken.JSONKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonFalse
import org.junit.Test

class FalseDeserialization {
	@Test
	fun `false`() {
		val json = JSONKraken.deserialize<JsonFalse>("false")
		assert(json == JsonFalse)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled false`() {
		JSONKraken.deserialize<JsonFalse>("fffff")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JSONKraken.deserialize<JsonFalse>("fals")
	}
}