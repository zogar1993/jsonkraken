package jemzart.kjson.values

class JsonInteger(override val value: Int): JsonLiteral() {
	override fun toString() = value.toString()
}