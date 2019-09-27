import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.values.JsonFalse
import org.junit.Test

class FalseDeserialization {
	@Test
	fun `false`() {
		val json = JsonKraken.deserialize<JsonFalse>("false")
		assert(json == JsonFalse)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled false`() {
		JsonKraken.deserialize<JsonFalse>("fffff")
	}

	@Test(expected = TokenExpectationException::class)
	fun `premature end`() {
		JsonKraken.deserialize<JsonFalse>("fals")
	}
}