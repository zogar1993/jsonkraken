package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.JsonContainer
import net.jemzart.jsonkraken.JsonObject
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

/**
 * @since 2.0
 * @return true if [value] is deeply contained within self.
 */
internal fun JsonContainer.references(value: JsonContainer): Boolean {
	return when (this){
		is JsonArray -> value.isReferencedBy(list)
		is JsonObject ->  value.isReferencedBy(hashMap.values)
	}
}