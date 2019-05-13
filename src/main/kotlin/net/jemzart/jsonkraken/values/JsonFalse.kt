package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

object JsonFalse : JsonValue() {
	override fun <T> cast(klass: KClass<*>): T {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}