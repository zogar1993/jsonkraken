import net.jemzart.jsonkraken.exceptions.TokenExpectationException
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class FalseDeserialization{
	@Test
	fun `false`(){
		val json = "false".toJson()
		assert(json == false)
	}

	@Test(expected = TokenExpectationException::class)
	fun `misspelled false`(){
		"fffff".toJson()
	}

	@Test(expected = TokenExpectationException::class)
	fun `unexpected EOF`(){
		"fals".toJson()
	}
}