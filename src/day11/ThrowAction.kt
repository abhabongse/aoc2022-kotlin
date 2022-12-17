package day11

/**
 * Algorithm for monkey to determine which other monkey to throw item to
 * according to divisibility test.
 */
data class ThrowAction(val testDivisibleBy: Long, val trueMonkeyTarget: Int, val falseMonkeyTarget: Int) {
    companion object {
        val testDivisibleByPattern = """Test: divisible by (\d+)""".toRegex()
        val trueMonkeyTargetPattern = """If true: throw to monkey (\d+)""".toRegex()
        val falseMonkeyTargetPattern = """If false: throw to monkey (\d+)""".toRegex()

        /** Creates an object by parsing the given strings. */
        fun from(testDivisibleText: String, trueMonkeyText: String, falseMonkeyText: String): ThrowAction {
            val (testDivisibleBy) = testDivisibleByPattern
                .matchEntire(testDivisibleText)
                ?.destructured
                ?: throw IllegalArgumentException("invalid pattern: $testDivisibleText")
            val (trueMonkeyTarget) = trueMonkeyTargetPattern
                .matchEntire(trueMonkeyText)
                ?.destructured
                ?: throw IllegalArgumentException("invalid pattern: $trueMonkeyText")
            val (falseMonkeyTarget) = falseMonkeyTargetPattern
                .matchEntire(falseMonkeyText)
                ?.destructured
                ?: throw IllegalArgumentException("invalid pattern: $falseMonkeyText")
            return ThrowAction(testDivisibleBy.toLong(), trueMonkeyTarget.toInt(), falseMonkeyTarget.toInt())
        }
    }

    /**
     * Finds the next monkey number based on the given worry level [item].
     */
    fun findNextMonkey(item: Long): Int = if (item % this.testDivisibleBy == 0L) {
        this.trueMonkeyTarget
    } else {
        this.falseMonkeyTarget
    }
}
