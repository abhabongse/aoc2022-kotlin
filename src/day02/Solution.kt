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
        val response = getProperResponse(it.opponentAction, it.p2DesiredOutcome)
        computeRoundScore(it.opponentAction, response)
    }
    println("Part 2: $p2Scores")
}

/** Reads and parses input data according to the problem statement. */
fun readInput(fileName: String): List<RoundStrategyGuide> {
    return File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { RoundStrategyGuide fromString it }
        .toList()
}
