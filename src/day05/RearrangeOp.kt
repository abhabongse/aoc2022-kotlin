package day05

/**
 * A rearrangement operation to move the given [count] of crates
 * from the [source] stack pile to the [dest] stack pile.
 */
data class RearrangeOp(val count: Int, val source: Char, val dest: Char) {
    companion object {
        private val pattern = """move (\d+) from (\d) to (\d)""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): RearrangeOp {
            val (count, source, dest) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            return RearrangeOp(count.toInt(), source.single(), dest.single())
        }
    }
}
