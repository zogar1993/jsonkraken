package jemzart.kjson.values

class JsonInteger internal constructor(override val value: Int) : JsonLiteral() {
	override fun toString() = value.toString()
}