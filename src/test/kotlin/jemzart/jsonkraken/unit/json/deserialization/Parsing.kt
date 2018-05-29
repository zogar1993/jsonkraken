package jemzart.jsonkraken.unit.json.deserialization

import jemzart.jsonkraken.helpers.asResourceFile
import jemzart.jsonkraken.toJson
import org.junit.Test

class Parsing{
	@Test
	fun mustParse() {
		var passed = 0
		var failed = 0
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'y')
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
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'n')
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
		"/test_parsing".asResourceFile().walk().forEach {
			if (!it.isDirectory) {
				val text = it.readText()
				if (it.name[0] == 'i')
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