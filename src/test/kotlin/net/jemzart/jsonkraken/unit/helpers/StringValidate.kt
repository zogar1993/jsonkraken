package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.helpers.validate
import org.junit.Test

class StringValidate {
	@Test
	fun `json string compliance`(){
		JsonStringCompliance.verify { value: Any -> value.toString().validate() }
	}
}