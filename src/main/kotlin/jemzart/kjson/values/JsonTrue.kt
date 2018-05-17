package jemzart.kjson.values

class JsonTrue : JsonLiteral() {
	companion object {
		private var created = false
	}
	init {
		assert(!created)
		created = true
	}

	override val value = true
	override fun toString() = "true"
}