package jemzart.kjson.values

import jemzart.kjson.jsonObject

interface JsonCollection : Iterable<Any?> {
	operator fun get(key: String): JsonCollection
	operator fun set(key: String, value: JsonCollection)
	operator fun <T>get(key: String, shamelessHack: T): T
	operator fun set(key: String, value: Any?)
	operator fun get(index: Int): JsonCollection
	operator fun set(index: Int, value: JsonCollection)
	operator fun <T>get(index: Int, shamelessHack: T): T
	operator fun set(index: Int, value: Any?)
	companion object {
		internal val dummy: JsonCollection = jsonObject()
	}
}