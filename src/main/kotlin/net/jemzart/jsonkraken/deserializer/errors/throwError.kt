package net.jemzart.jsonkraken.deserializer.errors

import net.jemzart.jsonkraken.deserializer.Deserializer

internal const val PREVIEW_OFFSET_BACK = 20
internal const val PREVIEW_OFFSET_FORWARD = 20

internal fun Deserializer.throwError(detail: String): Nothing {
	throw DeserializationException(
		index = index,
		detail = detail,
		snapshot = getErrorScreenshot(),
		raw = raw
	)
}

private fun Deserializer.getErrorScreenshot(): String {
	var left = raw.substring(if (leftHorizon) offsetBack else 0, index)
	var right = raw.substring(index, if (rightHorizon) offsetForward else last)
	left = (if (leftHorizon) ".. " else "") + left
	right += if (rightHorizon) " .." else ""
	val arrow = "^".padStart(left.length)
	return (left + right + "\n" + arrow)
}

private val Deserializer.offsetBack get() = index - PREVIEW_OFFSET_BACK
private val Deserializer.offsetForward get() = index + PREVIEW_OFFSET_FORWARD
private val Deserializer.leftHorizon get() = offsetBack >= 0
private val Deserializer.rightHorizon get() = offsetForward <= last