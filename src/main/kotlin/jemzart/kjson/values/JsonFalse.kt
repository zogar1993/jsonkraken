package jemzart.kjson.values

class JsonFalse internal constructor() : JsonLiteral() {
	override val value = false
	override fun toString() = "false"
}