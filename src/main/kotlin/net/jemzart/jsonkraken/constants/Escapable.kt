package net.jemzart.jsonkraken.constants

internal object Escapable {
	val monoChars = arrayOf('\"', '\\', '/', 'b', 'f', 'n', 'r', 't')
	val whiteSpaceChars = arrayOf('\n', '\t', '\r')
}