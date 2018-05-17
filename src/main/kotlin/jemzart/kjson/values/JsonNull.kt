package jemzart.kjson.values

class JsonNull internal constructor() : JsonLiteral() {
	override val value: Nothing? = null
	override fun toString() = "null"
}