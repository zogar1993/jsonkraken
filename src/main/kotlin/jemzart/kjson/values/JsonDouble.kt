package jemzart.kjson.values

class JsonDouble internal constructor(override val value: Double) : JsonLiteral() {
	override fun toString() = value.toString()
}