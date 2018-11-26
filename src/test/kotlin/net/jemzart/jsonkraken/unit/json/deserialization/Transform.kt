package net.jemzart.jsonkraken.unit.json.deserialization

import net.jemzart.jsonkraken.helpers.asResourceFile
import net.jemzart.jsonkraken.toJson
import org.junit.Test

class Transform{
	@Test
	fun transform(){
		var passed = 0
		var failed = 0
		"/test_transform".asResourceFile().walk().forEach {
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