package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.exceptions.CircularReferenceException

/**
 * Represents a json structure, may it be an array or an object.
 */
abstract class JsonContainer : JsonValue() {
	override operator fun get(name: String): JsonValue = get(name.toInt())
	override operator fun set(name: String, value: Any?): Unit = set(name.toInt(), value)
	override operator fun get(index: Int): JsonValue = get(index.toString())
	override operator fun set(index: Int, value: Any?): Unit = set(index.toString(), value)
	/**
	 * @return a deep clone of self, with no shared references.
	 */
	abstract fun clone(): JsonValue

	/**
	 * @return amount of values.
	 */
	abstract val size: Int

	/**
	 * @return true if [value] is deeply contained within self.
	 */
	protected abstract fun references(value: JsonContainer): Boolean

	internal fun isReferencedBy(value: Iterable<Any?>): Boolean {
		for (item in value)
			if (item == this) return true
			else if (item is JsonContainer && item.references(this)) return true
		return false
	}

	protected fun throwIfHasAReferenceOnMe(target: JsonValue) {
		if (target is JsonContainer) {
			if (target == this) throw CircularReferenceException(this, target)
			if (target.references(this)) throw CircularReferenceException(this, target)
		}
	}
}