package net.jemzart.jsonkraken.unit.purification

import net.jemzart.jsonkraken.utils.JsonStringCompliance
import net.jemzart.jsonkraken.validate
import org.junit.Test

class StringValidation {
	@Test
	fun `json string compliance`(){
		JsonStringCompliance.verify { value: Any -> value.toString().validate() }
	}
}