package net.jemzart.jsonkraken.errors.transformation

import net.jemzart.jsonkraken.JsonValue
import net.jemzart.jsonkraken.errors.JsonKrakenException
import kotlin.reflect.KClass

/**
 * @since 2.0
 * An exception used for when a JsonValue tries to be unboxed, but fails because it cannot be casted to said type.
 * See 'Valid Types' for more information. (https://github.com/zogar1993/jsonkraken#valid-types)
 *
 * @property[from] the type of the JsonValue.
 * @property[to] the type to witch the cast was intended.
 */
class InvalidCastException(val from: KClass<out JsonValue>, val to: KClass<*>)
	: JsonKrakenException("Could not extract a ${to.simpleName} from ${from.simpleName}")