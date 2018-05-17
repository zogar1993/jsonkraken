package jemzart.kjson.values

class JsonString(override val value: String): JsonLiteral() {
	override fun toString() = "\"$value\""
}