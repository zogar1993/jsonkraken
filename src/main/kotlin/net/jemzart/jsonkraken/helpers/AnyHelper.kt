package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.values.JsonValue

internal fun Any.isValidJsonType(): Boolean {
	return this is Byte || this is Short || this is Int || this is Long ||
		this is Float || this is Double || this is String || this is Boolean ||
		this is JsonValue
}