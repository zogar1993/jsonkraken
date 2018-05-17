package jemzart.kjson.values

class JsonDouble(override val value: Double) : JsonLiteral() {
	override fun toString() = value.toString()
}