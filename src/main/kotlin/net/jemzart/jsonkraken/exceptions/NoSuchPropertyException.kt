package net.jemzart.jsonkraken.exceptions

import net.jemzart.jsonkraken.JsonObject

/**
 * Used when a property is being extracted from a JsonObject, and it does not possess it.
 *
 * @property obj the JsonObject which did not have required property.
 * @property property said property.
 */
class NoSuchPropertyException(val property: String, val obj: JsonObject): Exception()