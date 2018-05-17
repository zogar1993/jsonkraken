package jemzart.kjson.values

class JsonTrue : JsonLiteral() {
	override val value = true
	override fun toString() = "true"
}