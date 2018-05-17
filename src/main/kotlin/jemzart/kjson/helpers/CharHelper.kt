package jemzart.kjson.helpers

internal fun Char.isWhiteSpaceOtherThanSpace(): Boolean {
	return this == '\n' || this == '\t' || this == '\r'
}

internal fun Char.isWhiteSpace(): Boolean {
	return this == ' ' || this.isWhiteSpaceOtherThanSpace()
}