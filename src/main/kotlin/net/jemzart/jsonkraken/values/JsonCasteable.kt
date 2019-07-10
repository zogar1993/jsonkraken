package net.jemzart.jsonkraken.values

import kotlin.reflect.KClass

interface JsonCasteable {
    val casts: Map<KClass<out Any>, (Any)->Any>
}