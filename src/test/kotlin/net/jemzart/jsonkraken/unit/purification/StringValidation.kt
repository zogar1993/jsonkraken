package net.jemzart.jsonkraken.unit.purification

import net.jemzart.jsonkraken.helpers.JsonStringCompliance
import net.jemzart.jsonkraken.helpers.validate
import org.junit.Test

class StringValidation {
	@Test
	fun valid(){
		JsonStringCompliance.verify { value: Any -> value.toString().validate() }
	}
}