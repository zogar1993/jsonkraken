package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantString

class JsonString internal constructor() : JsonValue() {
	var value: String = ""; internal set
	constructor(value: String): this() {
		throwIfIsNotAJsonCompliantString(value)
		this.value = value
	}

	override fun equals(other: Any?): Boolean {
		if (other !is JsonString) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}