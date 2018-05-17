package jemzart.kjson.values

class JsonFalse: JsonLiteral() {
	override val value = false
	override fun toString() = "false"
}