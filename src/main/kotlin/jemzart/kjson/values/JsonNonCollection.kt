package jemzart.kjson.values

class JsonNonCollection(val value: Any?) : JsonValue(){
	val list = listOf(value)
	override fun iterator(): Iterator<Any?> = list.iterator()
}