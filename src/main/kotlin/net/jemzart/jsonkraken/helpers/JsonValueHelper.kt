package net.jemzart.jsonkraken.helpers

import net.jemzart.jsonkraken.values.JsonContainer
import net.jemzart.jsonkraken.values.JsonValue

internal fun copy(value: JsonValue) = if (value is JsonContainer) value.clone() else value