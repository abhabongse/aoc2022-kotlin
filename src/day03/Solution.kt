/* Solution to Day 3: Rucksack Reorganization
 * https://adventofcode.com/2022/day/3
 */
package day03

import java.io.File

fun main() {
    val fileName =
//        "day03_sample.txt"
        "day03_input.txt"
    val rucksacks = readInput(fileName)

    // Part 1: sum of priorities for item types
    // that appear in both compartments of each rucksack
    val p1SumPriorities = rucksacks
        .asSequence()
        .map { it.firstCompartment.toSet() intersect it.secondCompartment.toSet() }
        .flatten()
        .map(ItemPriority::lookup)
        .sum()
    println("Part 1: $p1SumPriorities")

    // Part 2: sum of priorities for item types
    // that appear in each group of three consecutive rucksacks
    val p2SumPriorities = rucksacks
        .asSequence()
        .chunked(3)
        .map { it[0].content.toSet() intersect it[1].content.toSet() intersect it[2].content.toSet() }
        .flatten()
        .map(ItemPriority::lookup)
        .sum()
    println("Part 2: $p2SumPriorities")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<RoundStrategy> {
    return File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { RoundStrategy(it.trim()) }
        .toList()
}

data class RoundStrategy(val content: String) {
    val firstCompartment: String
        get() {
            val length = this.content.length
            return this.content.slice(0 until length / 2)
        }
    val secondCompartment: String
        get() {
            val length = this.content.length
            return this.content.slice(length / 2 until length)
        }
}

/**
 * Priority based on the given item type.
 */
object ItemPriority {
    private const val itemTypes = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    fun lookup(char: Char): Int {
        val index = itemTypes.indexOf(char)
        return if (index > 0) {
            index
        } else {
            throw IllegalArgumentException("invalid item type: $char")
        }
    }
}
