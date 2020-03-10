package net.jemzart.jsonkraken.errors.primitives

import net.jemzart.jsonkraken.JsonPrimitive
import net.jemzart.jsonkraken.errors.JsonKrakenException

/**
 * @since 2.0
 * An exception used 'get' or 'set' operation is called upon a JsonPrimitive.
 *
 * @property[primitive] JsonPrimitive on which the operation was being attempted.
 */
class CollectionOperationOnJsonPrimitiveException(val primitive: JsonPrimitive<*>, message: String)
	: JsonKrakenException(message)