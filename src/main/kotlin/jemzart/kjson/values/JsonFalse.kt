package jemzart.kjson.values

class JsonFalse : JsonLiteral() {
	companion object {
		private var created = false
	}
	init {
		assert(!created)
		created = true
	}
	override val value = false
	override fun toString() = "false"
}