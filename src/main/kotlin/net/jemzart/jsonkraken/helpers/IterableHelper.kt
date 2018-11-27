package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.values.JsonValue

internal fun Iterable<Any?>.references(value: JsonValue): Boolean {
	for (item in this)
		if (item == value) return true
		else if (item is JsonValue && item.references(value)) return true
	return false
}