package day02

/** Strategy guide planned by the elf for a single round of Rock Paper Scissors. */
data class RoundStrategyGuide(val first: Char, val second: Char) {
    companion object {
        private val pattern = """([ABC])\s+([XYZ])""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): RoundStrategyGuide {
            val (first, second) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            return RoundStrategyGuide(first.single(), second.single())
        }
    }

    /** Opponent's action as decrypted from the first column of the strategy guide. */
    val opponentAction: Action
        get() = when (this.first) {
            'A' -> Action.ROCK
            'B' -> Action.PAPER
            'C' -> Action.SCISSORS
            else -> throw IllegalArgumentException("invalid first char: ${this.first}")
        }

    /**
     * Planned response action as decrypted from the second column of the strategy guide,
     * according to one possible interpretation (part 1).
     */
    val p1ResponseAction: Action
        get() = when (this.second) {
            'X' -> Action.ROCK
            'Y' -> Action.PAPER
            'Z' -> Action.SCISSORS
            else -> throw IllegalArgumentException("invalid second char: ${this.second}")
        }

    /**
     * Desired outcome as decrypted from the second column of the strategy guide,
     * according to one possible interpretation (part 2).
     */
    val p2DesiredOutcome: Outcome
        get() = when (this.second) {
            'X' -> Outcome.LOSE
            'Y' -> Outcome.DRAW
            'Z' -> Outcome.WIN
            else -> throw IllegalArgumentException("invalid second char: ${this.second}")
        }
}
