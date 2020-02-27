package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.exceptions.CircularReferenceException
import org.junit.Test

class EdgeCasesTest {
	@Test(expected = CircularReferenceException::class)
	fun `JsonCollection fails because of circular dependency when a parent inside a non JsonValue collection is inserted`(){
		val child = JsonArray()
		val parent = JsonArray(child)
		val listWithParent = listOf(parent)

		child.add(listWithParent)
	}
}