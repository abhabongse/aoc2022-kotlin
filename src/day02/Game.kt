package day02

/** Possible actions for the game of Rock Paper Scissors. */
enum class Action(val value: Int) {
    ROCK(0), PAPER(1), SCISSORS(2);

    companion object {
        /** Reverse lookup for action enum object based of [value]. */
        infix fun from(value: Int): Action =
            Action.values()
                .firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("unknown value for action: $value")
    }

    /** Determines the outcome based on the action of two players. */
    infix fun playAgainst(opponent: Action): Outcome =
        Outcome from Math.floorMod(this.value - opponent.value, 3)
}

/**
 * Possible outcomes for the game of Rock Paper Scissors.
 * The [value] is assigned so that the following identity holds:
 *     outcome ≡ response - opponent (mod 3)
 */
enum class Outcome(val value: Int) {
    WIN(1), DRAW(0), LOSE(2);

    companion object {
        /** Reverse lookup for outcome enum object based of [value]. */
        infix fun from(value: Int): Outcome =
            Outcome.values()
                .firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("unknown value for outcome: $value")
    }
}

/**
 * Determines the proper response to the [opponent]'s action
 * in order to obtain the [desiredOutcome].
 */
fun getProperResponse(opponent: Action, desiredOutcome: Outcome): Action =
    Action from Math.floorMod(desiredOutcome.value + opponent.value, 3)

/**
 * Computes the score for a single round of Rock Paper Scissors
 * based on actions of the [opponent] and your own [response].
 */
fun computeRoundScore(opponent: Action, response: Action): Int {
    val outcome = response playAgainst opponent
    val outcomeScore = when (outcome) {
        Outcome.WIN -> 6
        Outcome.DRAW -> 3
        Outcome.LOSE -> 0
    }
    val responseScore = when (response) {
        Action.ROCK -> 1
        Action.PAPER -> 2
        Action.SCISSORS -> 3
    }
    return outcomeScore + responseScore
}
