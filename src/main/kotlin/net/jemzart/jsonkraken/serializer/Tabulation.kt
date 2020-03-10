package net.jemzart.jsonkraken.serializer

/**
 * @since 2.0
 * Contains allowed tabulation options for serializing.
 */
enum class Tabulation(val value: String) {
	None(""),
	TwoSpace("  "),
	ThreeSpace("   "),
	FourSpace("    "),
	Tab("\t")
}