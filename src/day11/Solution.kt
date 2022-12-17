/* Solution to Day 11: Monkey in the Middle
 * https://adventofcode.com/2022/day/11
 */
package day11

import utils.largestBy
import utils.otherwiseThrow
import utils.splitAt
import java.io.File

fun main() {
    val fileName =
//        "day11_sample.txt"
        "day11_input.txt"
    val (monkeyAlgorithms, itemQueues) = readInput(fileName)

    // Part 1:
    repeat(20) {
        itemQueues.withIndex().forEach { (monkeyNo, itemQueue) ->
            while (itemQueue.isNotEmpty()) {
                val item = itemQueue.dequeue()
                val (modifiedItem, nextMonkeyNo) = monkeyAlgorithms[monkeyNo].apply(item)
                itemQueues[nextMonkeyNo].enqueue(modifiedItem)
            }
        }
    }
    val p1MonkeyBusiness = itemQueues
        .asSequence()
        .map { it.dequeueCount }
        .largestBy(2) { it }
        .let { (fst, snd) -> fst * snd }
    println("Part 1: $p1MonkeyBusiness")

    // Part 2:
    val p2MonkeyBusiness = 0
    println("Part 1: $p2MonkeyBusiness")
}

/** Reads and parses input data according to the problem statement. */
fun readInput(fileName: String): Pair<List<MonkeyAlgorithm>, List<ItemQueue>> {
    val monkeyAlgorithms: ArrayList<MonkeyAlgorithm> = arrayListOf()
    val itemQueues: ArrayList<ItemQueue> = arrayListOf()

    val lineGroups = File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { it.trim() }
        .splitAt { it.isEmpty() }

    for ((monkeyNo, lines) in lineGroups.withIndex()) {
        (monkeyNo == parseMonkeyNo(lines[0]))
            .otherwiseThrow { IllegalArgumentException("unmatched monkey number") }
        val initialItemQueue = ItemQueue from lines[1]
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
