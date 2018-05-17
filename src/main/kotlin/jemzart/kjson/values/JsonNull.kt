package jemzart.kjson.values

class JsonNull : JsonLiteral() {
	companion object {
		private var created = false
	}
	init {
		assert(!created)
		created = true
	}
	override val value: Nothing? = null
	override fun toString() = "null"
}