/* Solution to Day 10: Cathode-Ray Tube
 * https://adventofcode.com/2022/day/10
 */
package day10

import utils.thenOrNull
import utils.toDebugString
import java.io.File

fun main() {
    val fileName =
//        "day10_sample_a.txt"
        "day10_sample_b.txt"
//        "day10_input.txt"
    val cpuInstructions = readInput(fileName)
    print(cpuInstructions.toDebugString())

    // Part 1:
    val p1SumSignalStrengths = 0
    println("Part 1: $p1SumSignalStrengths")

    // Part 2:
    val p2TailSpots = 0
    println("Part 2: $p2TailSpots")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<CpuInstruction> {
    return File("inputs", fileName)
        .readLines()
        .map { CpuInstruction fromString it }
}

/**
 * CPU Instruction
 */
sealed class CpuInstruction(val numCycles: Int) {
    /**
     * Represents noop instruction (do nothing)
     */
    class Noop() : CpuInstruction(1) {
        companion object {
            infix fun fromString(string: String): Noop? =
                (string.trim() == "noop").thenOrNull { Noop() }
        }

        override fun toString(): String {
            return "Noop()"
        }
    }

    /**
     * Represents adding the literal value to the register X
     */
    data class AddX(val value: Int) : CpuInstruction(2) {
        companion object {
            val pattern = """addx (-?\d+)""".toRegex()
            infix fun fromString(string: String): AddX? =
                pattern.matchEntire(string.trim())
                    ?.destructured
                    ?.let { (value) -> AddX(value.toInt()) }
        }
    }

    companion object {
        infix fun fromString(string: String): CpuInstruction =
            (Noop fromString string)
                ?: (AddX fromString string)
                ?: throw IllegalArgumentException("unknown instruction: $string")
    }
}
