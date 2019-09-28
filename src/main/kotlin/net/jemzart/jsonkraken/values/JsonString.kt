package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantString

class JsonString(val value: String) : JsonValue() {
	init {
		throwIfIsNotAJsonCompliantString(value)
	}

	override fun equals(other: Any?): Boolean {
		if (other !is JsonString) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}