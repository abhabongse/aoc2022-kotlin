/* Solution to Day 11: Monkey in the Middle
 * https://adventofcode.com/2022/day/11
 */
package day11

import utils.MathUtil
import utils.largest
import utils.otherwiseThrow
import utils.splitAt
import java.io.File

fun main() {
    val fileName =
//        "day11_sample.txt"
        "day11_input.txt"
    val (monkeyAlgorithms, itemQueues) = readInput(fileName)

    // Part 1: simulate 20 rounds, with discount rate of worry level = 3
    run {
        val itemQueues = itemQueues.clone()
        repeat(20) { simulateRound(monkeyAlgorithms, itemQueues, 3L) }
        val p1MonkeyBusinessLevel = itemQueues
            .asSequence()
            .map { it.popFrontCount }
            .largest(2)
            .let { (fst, snd) -> fst * snd }
        println("Part 1: $p1MonkeyBusinessLevel")
    }

    // Part 2: simulate 10_000 rounds, no discount rate of worry level
    run {
        val itemQueues = itemQueues.clone()
        val modulus = monkeyAlgorithms.map { it.throwAction.testDivisibleBy }.let(MathUtil::lcm)
        repeat(10_000) {
            simulateRound(monkeyAlgorithms, itemQueues)
            itemQueues.forEach { it.resetMod(modulus) }
        }
        val p2MonkeyBusinessLevel = itemQueues
            .asSequence()
            .map { it.popFrontCount }
            .largest(2)
            .let { (fst, snd) -> fst.toLong() * snd.toLong() }
        println("Part 2: $p2MonkeyBusinessLevel")
    }
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

    // Process each monkey information
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

/**
 * Simulates a round of monkey throwing items around.
 * The variable [itemQueues] will be modified in-place.
 */
fun simulateRound(monkeyAlgorithms: List<MonkeyAlgorithm>, itemQueues: List<ItemQueue>, discountRatio: Long = 1) {
    for ((monkeyNo, itemQueue) in itemQueues.withIndex()) {
        while (itemQueue.isNotEmpty()) {
            val item = itemQueue.popFront()
            val (modifiedItem, nextMonkeyNo) = monkeyAlgorithms[monkeyNo].apply(item, discountRatio)
            itemQueues[nextMonkeyNo].pushBack(modifiedItem)
        }
    }
}
