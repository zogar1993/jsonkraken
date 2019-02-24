package net.jemzart.jsonkraken.unit.helpers

import net.jemzart.jsonkraken.helpers.validate
import net.jemzart.jsonkraken.utils.JsonStringCompliance
import org.junit.Test

class StringValidate {
	@Test
	fun `json string compliance`(){
		JsonStringCompliance.verify { value: Any -> value.toString().validate() }
	}
}