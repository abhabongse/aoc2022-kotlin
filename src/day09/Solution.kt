/* Solution to Day 9: Rope Bridge
 * https://adventofcode.com/2022/day/9
 */
package day09

import utils.FourDirection
import utils.Vec2
import utils.accumulate
import java.io.File
import kotlin.math.sign

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
 * The state of a rope represented by the list of location
 * of each knot in the rope in that order from head to tail.
 */
data class Rope(val positions: List<Vec2>) {
    companion object {
        fun atOrigin(length: Int) = Rope(List(length) { Vec2.zero })
    }

    val head get() = this.positions.first()
    val tail get() = this.positions.last()

    /**
     * Moves the current rope to the next state
     * using the given direction instruction for the head knot.
     */
    infix fun transitionBy(headDirection: FourDirection): Rope {
        val phantomHead = this.head + headDirection.value * 2
        val newPositions = this.positions
            .asSequence()
            .accumulate(phantomHead) { newPrevPosition, oldCurrPosition ->
                val gradient = (newPrevPosition - oldCurrPosition).let { diff ->
                    if (diff.normMax() > 1) {
                        Vec2(diff.x.sign, diff.y.sign)
                    } else {
                        Vec2.zero
                    }
                }
                oldCurrPosition + gradient
            }
            .toList()
        return Rope(newPositions)
    }
}
