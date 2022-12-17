/* Solution to Day 11: Monkey in the Middle
 * https://adventofcode.com/2022/day/11
 */
package day11

import utils.otherwiseThrow
import utils.splitAt
import utils.toDebugString
import java.io.File

fun main() {
    val fileName =
//        "day11_sample.txt"
        "day11_input.txt"
    val (monkeyAlgorithms, itemQueues) = readInput(fileName)
    println(monkeyAlgorithms.toDebugString())
    println(itemQueues.toDebugString())

    // Part 1:
    val p1Solution = 0
    println("Part 1: $p1Solution")

    // Part 2:
    val p2Solution = 0
    println("Part 1: $p2Solution")
}

/** Reads and parses input data according to the problem statement. */
fun readInput(fileName: String): Pair<List<MonkeyAlgorithm>, List<ItemQueue>> {
    var monkeyAlgorithms: ArrayList<MonkeyAlgorithm> = arrayListOf()
    var itemQueues: ArrayList<ItemQueue> = arrayListOf()

    var lineGroups = File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { it.trim() }
        .splitAt { it.isEmpty() }

    for ((monkeyNo, lines) in lineGroups.withIndex()) {
        (monkeyNo == parseMonkeyNo(lines[0]))
            .otherwiseThrow { IllegalArgumentException("unmatched monkey number") }
        val initialItemQueue = parseInitialItemQueue(lines[1])
        val modifyAction = ModifyAction from lines[2]
        val throwAction = ThrowAction.from(lines[3], lines[4], lines[5])
        val monkeyAlgorithm = MonkeyAlgorithm(modifyAction, throwAction)
        monkeyAlgorithms.add(monkeyAlgorithm)
        itemQueues.add(initialItemQueue)
    }
    return Pair(monkeyAlgorithms, itemQueues)
}

private val monkeyNoPattern = """Monkey (\d+):""".toRegex()

/**
 * Parses an input line representing the initial item queue for a monkey.
 */
fun parseMonkeyNo(string: String): Int {
    val (monkeyNo) = monkeyNoPattern
        .matchEntire(string)
        ?.destructured
        ?: throw IllegalArgumentException("invalid monkey number format: $string")
    return monkeyNo.toInt()
}
