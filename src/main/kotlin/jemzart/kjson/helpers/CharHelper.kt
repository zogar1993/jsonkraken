package jemzart.kjson.helpers

internal fun Char.isWhiteSpaceOtherThanSpace(): Boolean {
	return this == '\n' || this == '\t' || this == '\r'
}

internal fun Char.isWhiteSpace(): Boolean {
	return this == ' ' || this.isWhiteSpaceOtherThanSpace()
}

internal fun Char.isISOControlCharacterOtherThanDelete(): Boolean {
	val codePoint = this.toInt()
	// optimized from:
	// return if (this.toInt() == 0x7F) false else this.isISOControl()
	return codePoint <= 0x9F && (codePoint > 0x7F || codePoint.ushr(5) == 0)
}