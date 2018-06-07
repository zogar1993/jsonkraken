package jemzart.jsonkraken.values

class JsonObject : JsonValue() {
	override val size: Int get() = map.size
	private val map: MutableMap<String, Any?> = mutableMapOf()

	override fun iterator(): Iterator<Pair<String, Any?>> = map.map { it.key to it.value }.iterator()
	override fun get(name: String): Any? = map[name]
	override fun set(name: String, value: Any?){
		map[name] = value
	}

	override fun remove(name: String){
		map.remove(name)
	}

	override fun exists(name: String): Boolean {
		return map.containsKey(name)
	}

	override fun get(index: Int): Any? = get(index.toString())
	override fun set(index: Int, value: Any?) = set(index.toString(), value)
	override fun remove(index: Int) = remove(index.toString())
	override fun exists(index: Int): Boolean = exists(index.toString())
}