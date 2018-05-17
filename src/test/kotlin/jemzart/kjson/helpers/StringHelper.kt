package jemzart.kjson.helpers

import java.io.File

fun String.asResourceFile(): File = File(this.javaClass::class.java.getResource(this).toURI())