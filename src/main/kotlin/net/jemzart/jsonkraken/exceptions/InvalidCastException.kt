package net.jemzart.jsonkraken.exceptions

import kotlin.reflect.KClass

class InvalidCastException (val from: KClass<*>, val to: KClass<*>): Exception()