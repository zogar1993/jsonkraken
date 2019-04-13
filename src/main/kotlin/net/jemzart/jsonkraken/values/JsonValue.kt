package net.jemzart.jsonkraken.values

interface JsonValue {
	/**
	 * @return the value of the property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	operator fun get(name: String): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in a property named [name].
	 * if JsonArray, [name] works as an index.
	 */
	operator fun set(name: String, value: Any?): Unit = throw NotImplementedError()

	/**
	 * @return the element at index [index].
	 * if JsonObject, [index] works as a property name.
	 */
	operator fun get(index: Int): JsonValue = throw NotImplementedError()

	/**
	 * Sets [value] in the selected [index].
	 * if JsonObject, [index] works as a property name.
	 */
	operator fun set(index: Int, value: Any?): Unit = throw NotImplementedError()
}