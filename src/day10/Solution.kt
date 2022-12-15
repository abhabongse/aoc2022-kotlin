/* Solution to Day 10: Cathode-Ray Tube
 * https://adventofcode.com/2022/day/10
 */
package day10

import utils.accumulate
import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val fileName =
//        "day10_sample_a.txt"
//        "day10_sample_b.txt"
        "day10_input.txt"
    val cpuInstructions = readInput(fileName)

    // Part 1: signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles
    val signalStrengthsByCycle = cpuInstructions
        .asSequence()
        .flatMap {
            when (it) {
                is CpuInstruction.Noop -> listOf(0)
                is CpuInstruction.AddX -> listOf(0, it.value)
            }
        }
        .accumulate(1, includeInitial = true) { acc, gradient -> acc + gradient }
        .toList()
    val p1SumSignalStrengths = listOf(20, 60, 100, 140, 180, 220).sumOf { it * signalStrengthsByCycle[it - 1] }
    println("Part 1: $p1SumSignalStrengths")

    // Part 2:
    println("Part 2: (see below)")
    signalStrengthsByCycle
        .subList(0, 240)
        .asSequence()
        .chunked(40)
        .forEach { list ->
            list.withIndex()
                .forEach { (pixelColumn, regX) ->
                    val char = if ((regX - pixelColumn).absoluteValue <= 1) {
                        '#'
                    } else {
                        '.'
                    }
                    print(char)
                }
            println()
        }
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<CpuInstruction> {
    return File("inputs", fileName)
        .readLines()
        .map { CpuInstruction fromString it }
}
