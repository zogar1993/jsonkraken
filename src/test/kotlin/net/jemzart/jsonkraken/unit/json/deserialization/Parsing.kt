package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.helpers.asResourceFile
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class Parsing{
	@Test
	fun mustParse() {
		var passed = 0
		var failed = 0
		"/test_parsing/allowed".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					if (text == "[1\r\n]")
						text.toJson()
					passed++
				} catch (ex: Throwable) {
					failed++
				}
			}
		}
		println("MUST: $passed parsed, $failed not parsed")
		assert(failed == 0)
	}

	@Test
	fun mustNotParse() {
		var passed = 0
		var failed = 0
		"/test_parsing/restricted".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.toJson()
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
	fun mayParse() {
		var passed = 0
		var failed = 0
		"/test_parsing/optional".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				try {
					text.toJson()
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