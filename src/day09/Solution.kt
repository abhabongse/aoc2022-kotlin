/* Solution to Day 9: Rope Bridge
 * https://adventofcode.com/2022/day/9
 */
package day09

import utils.FourDirection
import utils.Vec2
import java.io.File

fun main() {
    val fileName =
        "day09_sample.txt"
//        "day09_input.txt"
    val moveInstructions = readInput(fileName)
    moveInstructions.forEachIndexed { index, moveInstruction -> println("$index, $moveInstruction") }

    // Part 1: number of positions visited by rope tail
    val p1VisitedPositions = 0
    println("Part 1: $p1VisitedPositions")

    // Part 2: ...
    val p2MatchedSize = 0
    println("Part 2: $p2MatchedSize")
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
 * Move instructions as appeared in input
 */
data class MoveInstruction(val direction: FourDirection, val count: Int) {
    companion object {
        infix fun fromString(string: String): MoveInstruction {
            val (dir, cnt) = string.trim().split("""\s+""".toRegex())
            val direction = when (dir.single()) {
                'U' -> FourDirection.NORTH
                'D' -> FourDirection.SOUTH
                'L' -> FourDirection.WEST
                'R' -> FourDirection.EAST
                else -> throw IllegalArgumentException("unknown direction: $dir")
            }
            val count = cnt.toInt()
            return MoveInstruction(direction, count)
        }
    }

    /**
     * Produces a sequence of individual moves of the same [direction]
     * for the [count] number of times.
     */
    fun iterator(): Sequence<FourDirection> {
        val instr = this
        return sequence {
            repeat(instr.count) {
                this.yield(instr.direction)
            }
        }
    }
}

/**
 * State of the rope, specifically the location of its [head] and [tail]
 */
data class RopeState(val head: Vec2, val tail: Vec2) {
}
