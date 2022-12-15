/* Solution to Day 9: Rope Bridge
 * https://adventofcode.com/2022/day/9
 */
package day09

import utils.accumulate
import java.io.File

fun main() {
    val fileName =
//        "day09_sample_a.txt"
//        "day09_sample_b.txt"
        "day09_input.txt"
    val moveInstructions = readInput(fileName)

    // Part 1: rope with 2 knots
    val p1RopeStates = simulateAndTrackRope(Rope.atOrigin(2), moveInstructions)
    val p1TailSpots = p1RopeStates.map { it.tail }.toSet().size
    println("Part 1: $p1TailSpots")

    // Part 2: rope with 10 knots
    val p2RopeStates = simulateAndTrackRope(Rope.atOrigin(10), moveInstructions)
    val p2TailSpots = p2RopeStates.map { it.tail }.toSet().size
    println("Part 2: $p2TailSpots")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<MoveInstruction> {
    return File("inputs", fileName)
        .readLines()
        .map { MoveInstruction fromString it }
}

/**
 * Simulates the experiment of moving the rope using the given instructions
 * and tracks all possible rope states happened during the simulation.
 */
fun simulateAndTrackRope(initialRope: Rope, moveInstructions: List<MoveInstruction>): Sequence<Rope> =
    moveInstructions
        .asSequence()
        .flatMap { it.iterator() }
        .accumulate(initialRope) { currentState, direction -> currentState transitionBy direction }
