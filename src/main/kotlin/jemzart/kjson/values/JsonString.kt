package jemzart.kjson.values

class JsonString internal constructor(override val value: String) : JsonLiteral() {
	override fun toString() = "\"$value\""
}