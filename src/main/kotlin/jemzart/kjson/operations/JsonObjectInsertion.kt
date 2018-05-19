package jemzart.kjson.operations

import jemzart.kjson.enums.JsonObjectPosition

class JsonObjectInsertion<A,B>(val pair: Pair<A,B>, val position: JsonObjectPosition, val relativeTo: String)