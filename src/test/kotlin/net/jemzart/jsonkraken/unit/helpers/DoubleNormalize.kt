package net.jemzart.jsonkraken.unit.helpers

import normalize
import org.junit.Test

class DoubleNormalize{
	@Test
	fun `zero stays zero`(){
		assert ((0.0).normalize() == 0.0)
	}

	@Test
	fun `minus zero changes to zero`(){
		assert ((-0.0).normalize() == 0.0)
	}

	@Test
	fun `one stays one`(){
		assert ((1.0).normalize() == 1.0)
	}

	@Test
	fun `minus one stays minus one`(){
		assert ((-1.0).normalize() == -1.0)
	}
}