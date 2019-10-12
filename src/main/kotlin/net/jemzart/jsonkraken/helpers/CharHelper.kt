package net.jemzart.jsonkraken.helpers

internal fun Char.isWhiteSpace(): Boolean {
	return this == ' ' || this == '\n' || this == '\t' || this == '\r'
}

internal fun Char.isISOControlCharacterOtherThanDelete(): Boolean {
	val codePoint = this.toInt()
	return codePoint <= 0x9F && (codePoint > 0x7F || codePoint.ushr(5) == 0)
}

internal fun Char.isHexadecimal(): Boolean {
	val codePoint = this.toByte()
	return codePoint in 48..57  // 0-9
		|| codePoint in 65..70  // A-F
		|| codePoint in 97..102 // a-f
}

internal fun Char.isNotHexadecimal() = !this.isHexadecimal()