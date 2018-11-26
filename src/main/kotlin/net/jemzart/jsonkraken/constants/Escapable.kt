package net.jemzart.jsonkraken.constants

internal class Escapable{
	companion object {
		val monoChars = arrayOf('\"', '\\', '/', 'b', 'f', 'n','r','t')
		val whiteSpaceChars = arrayOf('\n', '\t', '\r')
	}
}