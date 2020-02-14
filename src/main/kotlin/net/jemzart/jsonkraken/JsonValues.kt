package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.exceptions.NoSuchIndexException
import net.jemzart.jsonkraken.exceptions.NoSuchPropertyException
import net.jemzart.jsonkraken.helpers.*
import net.jemzart.jsonkraken.helpers.copy
import net.jemzart.jsonkraken.helpers.isNullable
import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantNumber
import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantString
import net.jemzart.jsonkraken.purifier.purify

sealed class JsonValue {
	/**
	 * @return the value of the property named [key].
	 * if JsonArray, [key] works as an index.
	 */
	open operator fun get(key: String): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in a property named [key].
	 * if JsonArray, [key] works as an index.
	 */
	open operator fun set(key: String, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @return the element at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	open operator fun get(index: Int): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in the selected [index].
	 * if JsonObject, [index] works as a property name.
	 */
	open operator fun set(index: Int, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @return unboxed value.
	 */
	inline fun <reified T> cast(): T {
		when (this) {
			is JsonString -> when (T::class) {
				CharSequence::class, String::class, Any::class -> return this.value as T
			}
			is JsonNumber -> when (T::class) {
				Byte::class -> return this.value.toByte() as T
				Short::class -> return this.value.toShort() as T
				Int::class -> return this.value.toInt() as T
				Long::class -> return this.value.toLong() as T
				Float::class -> return this.value.toFloat() as T
				Double::class -> return this.value.toDouble() as T
				Number::class -> return this.value as T
				Any::class -> return this as T
			}
			is JsonNull -> if (isNullable<T>()) return null as T
			is JsonBoolean -> when (T::class) {
				Boolean::class, Any::class -> return this.value as T
			}
		}
		throw InvalidCastException(from = this::class, to = T::class)
	}

	override fun toString(): String {
		return JsonKraken.serialize(this)
	}
}

/**
 * Represents a json structure, may it be an array or an object.
 */
sealed class JsonContainer : JsonValue() {
	override operator fun get(key: String): JsonValue = get(key.toInt())
	override operator fun set(key: String, value: Any?): Unit = set(key.toInt(), value)
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

/**
 * @constructor empty json array.
 */
class JsonArray internal constructor() : JsonContainer(), Collection<JsonValue> {
	private fun Int.reversible() = if (this < 0) list.size + this else this

	/**
	 * @constructor json array filled with [items].
	 * Items must be of valid types (See 'Valid Types').
	 */
	constructor(vararg items: Any?) : this() {
		for (item in items) {
			val purified = purify(item)
			list.add(purified)
		}
	}

	@PublishedApi
	internal val list: MutableList<JsonValue> = mutableListOf()

	override fun get(index: Int): JsonValue {
		val i = index.reversible()
		if (i !in 0 until list.size) throw NoSuchIndexException(index, this)
		return list[i]
	}

	override fun set(index: Int, value: Any?) {
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		for (i in list.size..index) list.add(JsonNull)
		list[index.reversible()] = purified
	}

	fun remove(index: Int) {
		list.removeAt(index.reversible())
	}

	/**
	 * adds [value] after the current end of the array.
	 */
	fun add(value: Any?) {
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		list.add(purified)
	}

	/**
	 * inserts [value] at specified [index].
	 * all elements from that index to the end are moved one step, and none is replaced.
	 */
	fun insert(index: Int, value: Any?) {
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		list.add(index.reversible(), purified)
	}

	override fun clone() = JsonArray(*list.map { copy(it) }.toTypedArray())

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(list)

	override val size get() = list.size
	override fun iterator() = list.iterator()
	override fun contains(element: JsonValue) = list.contains(element)
	override fun containsAll(elements: Collection<JsonValue>) = list.containsAll(elements)
	override fun isEmpty() = list.isEmpty()
}

//TODO Update Docs

/**
 * @constructor empty json object.
 */
class JsonObject internal constructor() : JsonContainer(), Map<String, JsonValue>, Iterable<Map.Entry<String, JsonValue>> {
	/**
	 * @constructor json object filled with [properties].
	 * Pair second values must be of valid types (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach { (name, value) ->
			throwIfIsNotAJsonCompliantString(name)
			hashMap[name] = purify(value)
		}
	}

	internal val hashMap: MutableMap<String, JsonValue> = mutableMapOf()

	override fun get(key: String) = hashMap[key] ?: throw NoSuchPropertyException(key, this)
	override fun set(key: String, value: Any?) {
		throwIfIsNotAJsonCompliantString(key)
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		hashMap[key] = purified
	}

	fun remove(key: String) {
		hashMap.remove(key)
	}

	override fun clone() = JsonObject(*hashMap.map { it.key to copy(it.value) }.toTypedArray())

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(hashMap.values)

	companion object {
		fun fromMap(map: Map<String, JsonValue>): JsonObject {
			val jsonObject = JsonObject()
			jsonObject.hashMap.putAll(map)
			return jsonObject
		}
	}

	override val size: Int get() = hashMap.size
	override operator fun iterator() = hashMap.iterator()
	override val entries get() = hashMap.entries
	override val keys get() = hashMap.keys
	override val values get() = hashMap.values
	override fun containsKey(key: String) = hashMap.containsKey(key)
	override fun containsValue(value: JsonValue) = hashMap.containsValue(value)
	override fun isEmpty() = hashMap.isEmpty()
}

sealed class JsonPrimitive<T> : JsonValue() {
	/**
	 * @property value raw value contained by the JsonValue.
	 */
	abstract val value: T
}

sealed class JsonBoolean : JsonPrimitive<Boolean>()

/**
 * @constructor JsonValue representation for 'false'.
 */
object JsonFalse : JsonBoolean() {
	override val value = false
}

/**
 * @constructor JsonValue representation for 'true'.
 */
object JsonTrue : JsonBoolean() {
	override val value = true
}

/**
 * @constructor JsonValue representation for 'null'.
 */
object JsonNull : JsonPrimitive<Nothing?>() {
	override val value = null
}

class JsonString internal constructor() : JsonPrimitive<String>() {
	override var value: String = ""; internal set
	constructor(value: String): this() {
		throwIfIsNotAJsonCompliantString(value)
		this.value = value
	}

	override fun equals(other: Any?) = other is JsonString && value == other.value
	override fun hashCode() = value.hashCode()
}

class JsonNumber internal constructor() : JsonPrimitive<String>() {
	override var value: String = ""; internal set

	//TODO reventar esta porqueria a tests
	constructor(value: Number): this("$value")
	constructor(value: String): this() {
		val number = value.trim()
		throwIfIsNotAJsonCompliantNumber(number)
		this.value = simplifyJsonNumber(number)
	}

	override fun equals(other: Any?) = other is JsonNumber && value == other.value
	override fun hashCode() = value.hashCode()
}
