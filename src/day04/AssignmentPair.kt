package day04

/** Represent one assignment pair of two integer ranges. */
data class AssignmentPair(val firstRange: IntRange, val secondRange: IntRange) {
    companion object {
        private val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): AssignmentPair {
            val (firstStart, firstEnd, secondStart, secondEnd) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            val firstRange = firstStart.toInt()..firstEnd.toInt()
            val secondRange = secondStart.toInt()..secondEnd.toInt()
            return AssignmentPair(firstRange, secondRange)
        }
    }
}
