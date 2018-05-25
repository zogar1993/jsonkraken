package jemzart.jsonkraken.values

class JsonNonCollection(val value: Any?) : JsonValue(){
	override val size: Int get() = 1
	val list = listOf(value)
	override fun iterator(): Iterator<Any?> = list.iterator()
}