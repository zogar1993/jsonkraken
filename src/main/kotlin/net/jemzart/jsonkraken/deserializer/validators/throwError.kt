package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer
import net.jemzart.jsonkraken.exceptions.TokenExpectationException

const val PREVIEW_OFFSET_BACK = 20
const val PREVIEW_OFFSET_FORWARD = 20
const val VERIFYING_END_OF_PARSE = "verifying end of parse"

internal fun Deserializer.throwError(context: String, detail: String) {
		val message =
			"\nError $errorLocalization while $context." +
				"\n$detail" +
				"\n$errorPreview"
		throw TokenExpectationException(message)
	}

private val Deserializer.errorLocalization: String get() = "at character $index"

private val Deserializer.errorPreview: String
	get() {
		val offsetBack = index - PREVIEW_OFFSET_BACK
		val offsetForward = index + PREVIEW_OFFSET_FORWARD
		val leftHorizon = offsetBack >= 0
		val rightHorizon = offsetForward <= last
		var left = raw.substring(if (leftHorizon) offsetBack else 0, index)
		var right = raw.substring(index, if (rightHorizon) offsetForward else last)
		left = (if (leftHorizon) ".. " else "") + left
		right += if (rightHorizon) " .." else ""
		val arrow = "^".padStart(left.length + 1)
		return (left + right + "\n" + arrow)
	}