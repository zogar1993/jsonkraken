package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.values.JsonContainer

internal fun Iterable<Any?>.references(value: JsonContainer): Boolean {
	for (item in this)
		if (item == value) return true
		else if (item is JsonContainer && item.references(value)) return true
	return false
}