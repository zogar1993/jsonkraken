package net.jemzart.jsonkraken.values

import java.lang.Exception

//TODO validate NaN and infinity
class JsonNumber internal constructor() : JsonValue() {
	var value: String = ""; internal set
	constructor(number: Number): this("$number")
	constructor(string: String): this() {
		val trimmed = string.trim()
		if (trimmed.isEmpty()) throw Exception()//Todo
		val dec = '.' in trimmed
		val int = if (dec) trimmed.substringBefore('.') else trimmed
		val float = if (dec) trimmed.substringAfter('.') else "0"
		value = if (int == "-0" && float == "0") "0"
		else "$int${if (float.all { it == '0' }) "" else ".$float"}"
	}
	//TODO reventar esta porqueria a tests

	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}