package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.jsonDeserialize
import org.junit.Test
import java.io.File

class Parsing {
	private fun String.asResourceFile(): File =
		File(this.javaClass::class.java.getResource(this).toURI())

	@Test
	fun `must parse`() {
		var passed = 0
		var failed = 0
		"/test_parsing/allowed".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.jsonDeserialize()
					passed++
				} catch (ex: Throwable) {
					println("${it.name} $text")
					failed++
				}
			}
		}
		println("MUST: $passed parsed, $failed not parsed")
		assert(failed == 0)
	}

	@Test
	fun `must not parse`() {
		var passed = 0
		var failed = 0
		"/test_parsing/restricted".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.jsonDeserialize()
					println("${it.name} $text")
					passed++
				} catch (ex: Throwable) {
					failed++
				}
			}
		}
		println("MUST NOT: $passed parsed, $failed not parsed")
		assert(passed == 0)
	}

	@Test
	fun `may parse`() {
		var passed = 0
		var failed = 0
		"/test_parsing/optional".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.jsonDeserialize()
					passed++
				} catch (ex: Throwable) {
					println("${it.name} $text")
					failed++
				}
			}
		}
		println("MAY: $passed parsed, $failed not parsed")
	}

	@Test
	fun transform() {
		var passed = 0
		var failed = 0
		"/test_transform".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.jsonDeserialize()
					passed++
				} catch (ex: Throwable) {
					println("${it.name} $text")
					failed++
				}
			}
		}
		println("MAY: $passed parsed, $failed not parsed")
	}
}