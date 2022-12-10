/* Solution to Day 2: Rock Paper Scissors
 * https://adventofcode.com/2022/day/2
 */
package day02

import java.io.File

fun main() {
    val fileName =
//        "day02_sample.txt"
        "day02_input.txt"
    val roundStrategies = readInput(fileName)

    // Part 1: interpret the second column as the response to the opponent's action
    val p1Scores = roundStrategies.sumOf { computeRoundScore(it.opponentAction, it.p1ResponseAction) }
    println("Part 1: $p1Scores")

    // Part 2: interpret the second column as the desired outcome of the game
    val p2Scores = roundStrategies.sumOf {
        val response = properResponse(it.opponentAction, it.p2DesiredOutcome)
        computeRoundScore(it.opponentAction, response)
    }
    println("Part 2: $p2Scores")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<RoundStrategy> {
    return File("inputs", fileName)
        .readLines()
        .map { RoundStrategy fromString it }
        .toList()
}

data class RoundStrategy(val first: Char, val second: Char) {
    companion object {
        private val pattern = """([ABC])\s+([XYZ])""".toRegex()
        infix fun fromString(string: String): RoundStrategy {
            val (first, second) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            return RoundStrategy(first.single(), second.single())
        }
    }

    val opponentAction: Action
        get() = when (this.first) {
            'A' -> Action.ROCK
            'B' -> Action.PAPER
            'C' -> Action.SCISSORS
            else -> throw IllegalArgumentException("invalid first char: ${this.first}")
        }

    val p1ResponseAction: Action
        get() = when (this.second) {
            'X' -> Action.ROCK
            'Y' -> Action.PAPER
            'Z' -> Action.SCISSORS
            else -> throw IllegalArgumentException("invalid second char: ${this.second}")
        }

    val p2DesiredOutcome: Outcome
        get() = when (this.second) {
            'X' -> Outcome.LOSE
            'Y' -> Outcome.DRAW
            'Z' -> Outcome.WIN
            else -> throw IllegalArgumentException("invalid second char: ${this.second}")
        }
}

/**
 * Actions for the game of Rock Paper Scissors.
 */
enum class Action(val value: Int) {
    ROCK(0), PAPER(1), SCISSORS(2);

    companion object {
        infix fun from(value: Int): Action? =
            Action.values().firstOrNull { it.value == value }
    }

    /**
     * Determine the outcome based on actions of two players.
     */
    infix fun playAgainst(opponent: Action): Outcome {
        val outcome = Outcome from Math.floorMod(this.value - opponent.value, 3)
        return outcome!!
    }
}

/**
 * Outcomes for the game of Rock Paper Scissors.
 * The values are assigned so that the following congruence holds:
 *     outcome ≡ response - opponent (mod 3)
 */
enum class Outcome(val value: Int) {
    WIN(1), DRAW(0), LOSE(2);

    companion object {
        infix fun from(value: Int): Outcome? =
            Outcome.values().firstOrNull { it.value == value }
    }
}

/**
 * Computes the score for a single round of Rock Paper Scissors
 * based on actions of opponent's and your response's.
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

/**
 * Determine the proper response to the opponent's action
 * in order to obtain the desired outcome.
 */
fun properResponse(opponent: Action, desiredOutcome: Outcome): Action {
    val response = Action from Math.floorMod(desiredOutcome.value + opponent.value, 3)
    return response!!
}
