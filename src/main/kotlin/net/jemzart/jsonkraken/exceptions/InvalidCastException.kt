package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.JsonValue
import kotlin.reflect.KClass

/**
 * @since 2.0
 * An exception used for when a JsonValue tries to be parsed to an invalid Type
 * See 'Valid Types' for more information. (https://github.com/zogar1993/jsonkraken#valid-types)
 *
 * @property from the type of the JsonValue.
 * @property to the type to witch the cast was intended.
 */
class InvalidCastException(val from: KClass<out JsonValue>, val to: KClass<*>) : Exception()