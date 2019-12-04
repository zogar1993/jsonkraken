package net.jemzart.jsonkraken.values

import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import net.jemzart.jsonkraken.exceptions.InvalidCastException
import net.jemzart.jsonkraken.exceptions.NoSuchIndexException
import net.jemzart.jsonkraken.exceptions.NoSuchPropertyException
import net.jemzart.jsonkraken.helpers.copy
import net.jemzart.jsonkraken.helpers.isNullable
import net.jemzart.jsonkraken.helpers.throwIfIsNotAJsonCompliantString
import net.jemzart.jsonkraken.purifier.purify

import java.lang.Exception

sealed class JsonValue {
	/**
	 * @return the value of the property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	open operator fun get(name: String): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in a property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	open operator fun set(name: String, value: Any?): Unit = throw NotImplementedError()

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
			is JsonBoolean -> return this.value as T //TODO fix whatever makes the validation fail
//			is JsonBoolean -> when (T::class) {
//				Boolean::class, Any::class -> this.value as T
//			}
		}
		throw InvalidCastException(from = this::class, to = T::class)
	}
}

/**
 * Represents a json structure, may it be an array or an object.
 */
sealed class JsonContainer : JsonValue() {
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

/**
 * @constructor empty json array.
 */
class JsonArray() : JsonContainer(), Iterable<JsonValue> {
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

	override val size: Int get() = list.size
	internal val list: MutableList<JsonValue> = mutableListOf()

	/**
	 * @return an iterator over all its items.
	 */
	override fun iterator(): Iterator<JsonValue> = list.iterator()

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
}

//TODO Update Docs

/**
 * @constructor empty json object.
 */
class JsonObject() : JsonContainer(), Iterable<Pair<String, JsonValue>> {
	/**
	 * @constructor json object filled with [properties].
	 * Pair second values must be of valid types (See 'Valid Types').
	 */
	constructor(vararg properties: Pair<String, Any?>) : this() {
		properties.forEach { (name, value) ->
			throwIfIsNotAJsonCompliantString(name)
			map[name] = purify(value)
		}
	}

	override val size: Int get() = map.size
	internal val map: MutableMap<String, JsonValue> = mutableMapOf()

	/**
	 * @return an iterator over all its properties.
	 */
	override fun iterator(): Iterator<Pair<String, JsonValue>> = map.map { it.key to it.value }.iterator()

	override fun get(name: String): JsonValue =
		if (map.containsKey(name)) map.getValue(name) else throw NoSuchPropertyException(name, this)
	override fun set(name: String, value: Any?) {
		throwIfIsNotAJsonCompliantString(name)
		val purified = purify(value)
		throwIfHasAReferenceOnMe(purified)
		map[name] = purified
	}

	fun remove(name: String) {
		map.remove(name)
	}

	/**
	 * @return a collection with all its property keys.
	 */
	val keys get() = map.keys

	/**
	 * @return a collection with all its property values.
	 */
	val values get() = map.values

	override fun clone() = JsonObject(*map { it.first to copy(it.second) }.toTypedArray())

	override fun references(value: JsonContainer): Boolean = value.isReferencedBy(map.values)

	companion object {
		fun fromMap(map: Map<String, JsonValue>): JsonObject {
			val jsonObject = JsonObject()
			jsonObject.map.putAll(map)
			return jsonObject
		}
	}
}

sealed class JsonPrimitive<T> : JsonValue() {
	abstract val value: T
}

sealed class JsonBoolean : JsonPrimitive<Boolean>()

object JsonFalse : JsonBoolean() {
	override val value = false
}

object JsonTrue : JsonBoolean() {
	override val value = true
}

object JsonNull : JsonPrimitive<Nothing?>() {
	override val value = null
}

class JsonString internal constructor() : JsonPrimitive<String>() {
	override var value: String = ""; internal set
	constructor(value: String): this() {
		throwIfIsNotAJsonCompliantString(value)
		this.value = value
	}

	override fun equals(other: Any?): Boolean {
		if (other !is JsonString) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}
}

//TODO validate NaN and infinity
class JsonNumber internal constructor() : JsonPrimitive<Number>() {
	override var value: Number = 0; internal set
	constructor(number: Number): this("$number")
	constructor(string: String): this() {
		val trimmed = string.trim()
		if (trimmed.isEmpty()) throw Exception()//Todo
		val dec = '.' in trimmed
		val int = if (dec) trimmed.substringBefore('.') else trimmed
		val float = if (dec) trimmed.substringAfter('.') else "0"
		val str = if (int == "-0" && float == "0") "0"
		else "$int${if (float.all { it == '0' }) "" else ".$float"}"
		value = PlainNumber(str)
	}
	//TODO reventar esta porqueria a tests

	override fun equals(other: Any?): Boolean {
		if (other !is JsonNumber) return false
		return value == other.value
	}

	override fun hashCode(): Int {
		return value.hashCode()
	}

	class PlainNumber(private val value: String): Number() {
		override fun toByte() = value.toByte()
		override fun toChar() = value.toLong().toChar()
		override fun toDouble() = value.toDouble()
		override fun toFloat() = value.toFloat()
		override fun toInt() = value.toInt()
		override fun toLong() = value.toLong()
		override fun toShort() = value.toShort()

		override fun equals(other: Any?): Boolean {
			if (other !is PlainNumber) return false
			return value == other.value
		}

		override fun hashCode(): Int {
			return value.hashCode()
		}

		override fun toString() = value
	}
}