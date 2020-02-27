package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.JsonContainer
import net.jemzart.jsonkraken.JsonValue
import net.jemzart.jsonkraken.errors.collections.CircularReferenceException

internal fun JsonContainer.isReferencedBy(value: Iterable<Any?>): Boolean {
	for (item in value)
		if (item == this) return true
		else if (item is JsonContainer && item.references(this)) return true
	return false
}

internal fun JsonContainer.throwIfHasAReferenceOnMe(target: JsonValue) {
	if (target is JsonContainer) {
		if (target == this) throw CircularReferenceException(this, target)
		if (target.references(this)) throw CircularReferenceException(this, target)
	}
}