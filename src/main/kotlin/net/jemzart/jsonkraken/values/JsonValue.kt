package net.jemzart.jsonkraken.values

/**
 * Represents a json structure, may it be an array or an object.
 */
interface JsonValue {
	/**
	 * @return the value of the property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	operator fun get(name: String): Any? = get(name.toInt())
	/**
	 * Sets [value] in a property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	operator fun set(name: String, value: Any?): Unit = set(name.toInt(), value)
	/**
	 * Removes item of name [name].
	 * if JsonArray, [name] works as an index.
	 */
	fun remove(name: String): Unit = remove(name.toInt())
	/**
	 * @return true if an item of name [name] exists.
	 * if JsonArray, [name] works as an index.
	 */
	fun exists(name: String): Boolean = exists(name.toInt())
	/**
	 * @return the element at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	operator fun get(index: Int): Any? = get(index.toString())
	/**
	 * Sets [value] in the selected [index].
	 * if JsonObject, [index] works as a property name.
	 */
	operator fun set(index: Int, value: Any?): Unit = set(index.toString(), value)
	/**
	 * Removes item of at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	fun remove(index: Int): Unit = remove(index.toString())
	/**
	 * @return true if an item exists at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	fun exists(index: Int): Boolean = exists(index.toString())
	/**
	 * @return a deep clone of self, with no shared references.
	 */
	fun clone(): JsonValue
	/**
	 * @return amount of values.
	 */
	val size: Int
	/**
	 * @return true if [value] is deeply contained within self.
	 */
	fun references(value: JsonValue): Boolean
}