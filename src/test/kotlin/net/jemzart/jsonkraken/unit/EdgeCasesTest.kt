package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.JsonArray
import net.jemzart.jsonkraken.errors.collections.CircularReferenceException
import org.junit.Test

class EdgeCasesTest {
	@Test(expected = CircularReferenceException::class)
	fun `JsonCollection should fail because of circular dependency when an ancestor inside a non JsonValue collection is inserted into it`(){
		val child = JsonArray()
		val parent = JsonArray(child)
		val listWithParent = listOf(parent)

		child.add(listWithParent)
	}
}