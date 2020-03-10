package net.jemzart.jsonkraken

import net.jemzart.jsonkraken.errors.collections.InvalidIndexException
import net.jemzart.jsonkraken.errors.collections.NoSuchIndexException
import net.jemzart.jsonkraken.errors.collections.NoSuchPropertyException
import net.jemzart.jsonkraken.helpers.*
import net.jemzart.jsonkraken.purifier.purify

/**
 * @since 1.0
 * Represents any json value.
 */
sealed class JsonValue {
	/**
	 * @since 1.0
	 * @return the element of at key [key].
	 * if JsonArray, [key] works as an index.
	 */
	open operator fun get(key: String): JsonValue = throw NotImplementedError()

	/**
	 * @since 1.0
	 * Sets [value] as the element at key [key].
	 * if JsonArray, [key] works as an index.
	 */
	open operator fun set(key: String, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @since 1.0
	 * @return the element at index [index].
	 * if JsonObject, [index] works as a property key.
	 */
	open operator fun get(index: Int): JsonValue = throw NotImplementedError()//TODO add error specific to this cases

	/**
	 * @since 1.0
	 * Sets [value] as the element at index [index].
	 * if JsonObject, [index] works as a property key.
	 */
	open operator fun set(index: Int, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @since 2.0
	 * @return unboxed value.
	 */
	inline fun <reified T> cast(): T {
		return when (this) {
			is JsonString -> stringOrThrow(this.value)
			is JsonNumber -> numberOrThrow(this.value)
			is JsonBoolean -> booleanOrThrow(this.value)
			is JsonNull -> nullOrThrow()
			else -> throwInvalidCastException<T>()
		}
	}

	/**
	 * @since 2.0
	 * @return compact serialized version of the JsonValue.
	 */
	override fun toString() = JsonKraken.serialize(this)
}

/**
 * @since 2.0
 * Represents a json structure, may it be an array or an object.
 */
sealed class JsonContainer : JsonValue() {
	override operator fun get(key: String): JsonValue =
		get(key.toIntOrNull() ?: throw InvalidIndexException(key, this as JsonArray))
	override operator fun set(key: String, value: Any?): Unit =
		set(key.toIntOrNull() ?: throw InvalidIndexException(key, this as JsonArray), value)
	override operator fun get(index: Int): JsonValue = get(index.toString())
	override operator fun set(index: Int, value: Any?): Unit = set(index.toString(), value)

	/**
	 * @since 2.0
	 * @return a deep clone of self, with no shared references.
	 */
	abstract fun clone(): JsonValue

	/**
	 * @since 2.0
	 * @return amount of elements.
	 */
	abstract val size: Int

	/**
	 * @since 2.0
	 * @return true if the JsonCollection is empty, otherwise it returns false.
	 */
	abstract fun isEmpty(): Boolean

	/**
	 * @since 2.0
	 * @return true if the JsonCollection is not empty, otherwise it returns false.
	 */
	fun isNotEmpty() = !isEmpty()

	/**
	 * @since 2.0
	 * @return true if [value] is deeply contained within self.
	 */
	internal abstract fun references(value: JsonContainer): Boolean
}

/**
 * @since 1.0
 * JsonValue representation for 'array'.
 * @constructor empty json array.
 */
class JsonArray() : JsonContainer(), Iterable<JsonValue> {
	private fun Int.reversible() = if (this < 0) list.size + this else this

	/**
	 * @since 1.0
	 * @constructor json array filled with [items].
	 * Each item must be of a valid type (See 'Valid Types').
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

	/**
	 * @since 1.0
	 * removes item at [index].
	 */
	fun remove(index: Int) {
		list.removeAt(index.reversible())
	}

	/**
	 * @since 1.0
	 * adds [value] after the current end of the array.
	 */
	fun add(value: Any?) {
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		list.add(purified)
	}

	/**
	 * @since 1.0
	 * inserts [value] at specified [index].
	 * all elements from that index to the end are moved one step, and none is replaced.
	 */
	fun insert(index: Int, value: Any?) {
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		list.add(index.reversible(), purified)
	}

	override fun clone(): JsonArray = JsonKraken.transform(list.map { copy(it) })

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(list)

	override val size get() = list.size
	override fun iterator() = list.iterator()
	override fun isEmpty() = list.isEmpty()
}

/**
 * @since 1.0
 * JsonValue representation for 'object'.
 * @constructor empty json object.
 */
class JsonObject() : JsonContainer(), Iterable<Map.Entry<String, JsonValue>> {
	/**
	 * @since 1.0
	 * @constructor json object filled with [properties].
	 * Each pair first value must be compliant with the JSON specification.
	 * Each pair second value must be of a valid type (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach { (key, value) ->
			throwIfIsNotAJsonCompliantString(key)
			hashMap[key] = purify(value)
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


	/**
	 * @since 1.0
	 * removes element at key [key].
	 */
	fun remove(key: String) {
		hashMap.remove(key)
	}

	override fun clone(): JsonObject = JsonKraken.transform(hashMap.map { it.key to copy(it.value) }.toMap())

	override fun references(value: JsonContainer) = value.isReferencedBy(hashMap.values)

	/**
	 * @since 1.0
	 * retrieves all keys.
	 */
	val keys get() = hashMap.keys

	/**
	 * @since 1.0
	 * retrieves all values.
	 */
	val values get() = hashMap.values

	/**
	 * @since 2.0
	 * returns true if [key] exists.
	 */
	fun containsKey(key: String) = hashMap.containsKey(key)

	override val size: Int get() = hashMap.size
	override operator fun iterator() = hashMap.iterator()
	override fun isEmpty() = hashMap.isEmpty()
}

/**
 * @since 2.0
 * Represents a json primitive, either a boolean, string, number or null.
 */
sealed class JsonPrimitive<T> : JsonValue() {
	/**
	 * @since 2.0
	 * @property value raw value contained by the JsonValue.
	 */
	abstract val value: T
}

/**
 * @since 2.0
 * JsonValue representation for 'boolean'.
 */
sealed class JsonBoolean(override val value: Boolean) : JsonPrimitive<Boolean>()

/**
 * @since 2.0
 * JsonValue representation for 'false'.
 */
object JsonFalse : JsonBoolean(false)

/**
 * @since 2.0
 * JsonValue representation for 'true'.
 */
object JsonTrue : JsonBoolean(true)

/**
 * @since 2.0
 * JsonValue representation for 'null'.
 */
object JsonNull : JsonPrimitive<Nothing?>() {
	override val value = null
}

/**
 * @since 2.0
 * JsonValue representation for 'string'.
 */
class JsonString internal constructor() : JsonPrimitive<String>() {
	override var value: String = ""; internal set

	constructor(value: String) : this() {
		throwIfIsNotAJsonCompliantString(value)
		this.value = value
	}

	override fun equals(other: Any?) = other is JsonString && value == other.value
	override fun hashCode() = value.hashCode()
}

/**
 * @since 2.0
 * JsonValue representation for 'number'.
 */
class JsonNumber internal constructor() : JsonPrimitive<String>() {
	override var value: String = ""; internal set

	constructor(value: Number) : this("$value")
	constructor(value: String) : this() {
		val number = value.trim()
		throwIfIsNotAJsonCompliantNumber(number)
		this.value = simplifyJsonNumber(number)
	}

	override fun equals(other: Any?) = other is JsonNumber && value == other.value
	override fun hashCode() = value.hashCode()
}
