package jemzart.kjson.values

class JsonNull : JsonLiteral() {
	override val value = null
	override fun toString() = "null"
}