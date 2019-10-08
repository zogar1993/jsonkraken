package net.jemzart.jsonkraken.deserializer.validators

import net.jemzart.jsonkraken.deserializer.Deserializer

const val VERIFYING_END_OF_PARSE = "verifying end of parse"
internal fun Deserializer.validateEOF() {
	if (!isAtEnd())
		throwError(VERIFYING_END_OF_PARSE,
			"Invalid characters have been found after the end of the outermost json structure." +
				"Should they be removed, the parse would succeed.")
}

//todo add localization