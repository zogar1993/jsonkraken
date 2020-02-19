package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.JsonValue
import kotlin.reflect.KClass

/**
 * An exception used for when a specific type of JsonValue was expected, but received another.
 *
 * @property expected the expected type of the JsonValue.
 * @property actual the actual type of the JsonValue.
 */
class UnexpectedJsonValueException(val expected: KClass<out JsonValue>, val actual: KClass<out JsonValue>) : Exception()