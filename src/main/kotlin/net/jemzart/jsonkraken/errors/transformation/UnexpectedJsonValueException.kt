package net.jemzart.jsonkraken.errors.transformation

import net.jemzart.jsonkraken.JsonValue
import kotlin.reflect.KClass

/**
 * @since 2.0
 * An exception used for when a specific type of JsonValue was expected, but received another.
 *
 * @property[expected] the expected type of the JsonValue.
 * @property[actual] the actual type of the JsonValue.
 */
class UnexpectedJsonValueException(val expected: KClass<out JsonValue>, val actual: KClass<out JsonValue>) : Exception()