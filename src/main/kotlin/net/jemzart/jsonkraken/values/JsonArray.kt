package net.jemzart.jsonkraken.values

class JsonArray : JsonValue() {
	override val size: Int get() = list.size
	private val list: MutableList<Any?> = mutableListOf()

	override fun iterator(): Iterator<Any?> = list.iterator()

	@Suppress("UNCHECKED_CAST")
	override fun get(index: Int): Any? = list[if (index < 0) list.size + index else index]
	override fun set(index: Int, value: Any?) {
		for (i in list.size..index)	list.add(null)
		list[if (index < 0) list.size + index else index] = value
	}

	override fun remove(index: Int){
		list.removeAt(index)
	}

	override fun exists(index: Int): Boolean {
		return index < list.size
	}

	fun add(item: Any?){
		list.add(item)
	}

	fun insert(index: Int, value: Any?){
		list.add(index, value)
	}


	override fun get(name: String): Any? = get(name.toInt())
	override fun set(name: String, value: Any?) = set(name.toInt(), value)
	override fun remove(name: String) = remove(name.toInt())
	override fun exists(name: String): Boolean = exists(name.toInt())
}