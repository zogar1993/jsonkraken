package net.jemzart.jsonkraken.unit.json.value.number

import net.jemzart.jsonkraken.values.JsonNumber
import org.junit.Test

class JsonNumberCast {

	@Test
	fun `Byte cast`() {
		val byte = 13.toByte()
		val value = JsonNumber(byte)
		assert(value.cast<Byte>() == byte)
	}

	@Test
	fun `Short cast`() {
		val short = 13.toShort()
		val value = JsonNumber(short)
		assert(value.cast<Short>() == short)
	}

	@Test
	fun `Int cast`() {
		val int = 13
		val value = JsonNumber(int)
		assert(value.cast<Int>() == int)
	}

	@Test
	fun `Long cast`() {
		val long = 13.toLong()
		val value = JsonNumber(long)
		assert(value.cast<Long>() == long)
	}

	@Test
	fun `Float cast`() {
		val float = 13.toFloat()
		val value = JsonNumber(float)
		assert(value.cast<Float>() == float)
	}

	@Test
	fun `Double cast`() {
		val double = 13.toDouble()
		val value = JsonNumber(double)
		assert(value.cast<Double>() == double)
	}

	@Test
	fun `zero stays zero`() {
		val value = JsonNumber(0.0)
		assert(value.cast<Double>() == 0.0)
	}

	@Test
	fun `minus zero changes to zero`() {
		val value = JsonNumber(-0.0)
		assert(value.cast<Double>() == 0.0)
	}

	@Test
	fun `one stays one`() {
		val value = JsonNumber(1.0)
		assert(value.cast<Double>() == 1.0)
	}

	@Test
	fun `minus one stays minus one`() {
		val value = JsonNumber(-1.0)
		assert(value.cast<Double>() == -1.0)
	}

	@Test
	fun `casting to Any returns a JsonNumber`() {
		val json = JsonNumber(1)
		val result = json.cast<Any>()

		assert(JsonNumber(1) == result)
	}
}