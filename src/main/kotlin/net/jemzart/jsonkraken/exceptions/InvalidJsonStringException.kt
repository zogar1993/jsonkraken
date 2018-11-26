package net.jemzart.jsonkraken.exceptions

//TODO make exception happier
class InvalidJsonStringException(val value: String, val info: String) : Exception()