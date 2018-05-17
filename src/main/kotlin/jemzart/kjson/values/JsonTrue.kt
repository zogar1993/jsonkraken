package jemzart.kjson.values

class JsonTrue internal constructor() : JsonLiteral() {
	override val value = true
	override fun toString() = "true"
}