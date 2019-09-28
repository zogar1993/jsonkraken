package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.helpers.validateJsonStringCompliance

class JsonString(val value: String) : JsonValue() {
	init {
		value.validateJsonStringCompliance()
	}

	override fun equals(other: Any?): Boolean {
		if (other !is JsonString) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}