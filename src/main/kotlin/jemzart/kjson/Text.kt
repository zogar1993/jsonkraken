package jemzart.kjson

class Text(val string: String) {
	var start = 0
	var end = string.length
	val next get() = string[start]
	operator fun get(i: Int) = string[start+i]

	fun beforeIfAny(occurrence: (Char)->Boolean){
		for (i in start until end)
			if(occurrence(string[i])){
				end = i
				return
			}
	}

	override fun toString(): String {
		return string.substring(start, end)
	}

	fun skipSpaces() {
		for (i in start until end){
			val char = string[i]
			if(char != ' ' && char != '\t' && char != '\n'){
				start = i
				return
			}
		}
		end = start
	}

	fun indexOf(occurrence: (Char)->Boolean): Int {
		for (i in start until end)
			if(occurrence(string[i])){
				return i
			}
		return -1
	}
}