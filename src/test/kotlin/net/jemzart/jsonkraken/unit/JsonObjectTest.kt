package net.jemzart.jsonkraken.unit

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import net.jemzart.jsonkraken.values.JsonString
import org.junit.Test
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class JsonObjectTest {
	@Test
	fun added() {
		val obj = JsonObject()
		obj["name"] = "Ragoz"
		assert(obj["name"] == JsonString("Ragoz"))
	}

	@Test
	fun `simple array`() {
		val arr = JsonArray()
		arr.add("Ragoz")
		assert(arr[0] == JsonString("Ragoz"))
	}


	@Test
	fun `insert first`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(0, "Joelin")
		assert(obj[0] == JsonString("Joelin"))
		assert(obj[1] == JsonString("Von Chap"))
		assert(obj[2] == JsonString("Ulf"))
	}

	@Test
	fun `insert in the middle`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(1, "Joelin")
		assert(obj[1] == JsonString("Joelin"))
		assert(obj[0] == JsonString("Von Chap"))
		assert(obj[2] == JsonString("Ulf"))
	}

	@Test
	fun `insert last`() {
		val obj = JsonArray()
		obj.add("Von Chap")
		obj.add("Ulf")
		obj.insert(2, "Joelin")
		assert(obj[2] == JsonString("Joelin"))
		assert(obj[0] == JsonString("Von Chap"))
		assert(obj[1] == JsonString("Ulf"))
	}
}