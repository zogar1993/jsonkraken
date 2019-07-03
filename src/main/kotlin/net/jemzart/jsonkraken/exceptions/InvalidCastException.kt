package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.values.JsonValue
import kotlin.reflect.KClass

/**
 * An exception used for when a JsonValue tries to be parsed to an invalid Type
 *
 * @property from the type of the JsonValue.
 * @property to the type to witch the cast was intended.
 */
class InvalidCastException (val from: KClass<JsonValue>, val to: KClass<*>): Exception()