/* Solution to Day 4: Camp Cleanup
 * https://adventofcode.com/2022/day/4
 */
package day04

import java.io.File

fun main() {
    val fileName =
//        "day04_sample.txt"
        "day04_input.txt"
    val assignmentPairs = readInput(fileName)

    // Part 1: count assignment pairs where one fully contains the other
    val p1Count = assignmentPairs.count { (firstRange, secondRange) ->
        firstRange subset secondRange || secondRange subset firstRange
    }
    println("Part 1: $p1Count")

    // Part 2: count overlapping assignment pairs
    val p2Count = assignmentPairs.count { (firstRange, secondRange) ->
        firstRange overlaps secondRange
    }
    println("Part 2: $p2Count")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<AssignmentPair> {
    return File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { AssignmentPair fromString it }
        .toList()
}

data class AssignmentPair(val firstRange: IntRange, val secondRange: IntRange) {
    companion object {
        private val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
        infix fun fromString(string: String): AssignmentPair {
            val (firstStart, firstEnd, secondStart, secondEnd) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            val firstRange = firstStart.toInt()..firstEnd.toInt()
            val secondRange = secondStart.toInt()..secondEnd.toInt()
            return AssignmentPair(firstRange, secondRange)
        }
    }
}

/**
 * Checks if this IntRange is a subset of the other IntRange.
 */
infix fun IntRange.subset(other: IntRange): Boolean {
    return other.first <= this.first && this.last <= other.last
}

/**
 * Checks if this IntRange overlaps with the other IntRange
 * by at least one element.
 */
infix fun IntRange.overlaps(other: IntRange): Boolean {
    return this.first <= other.last && other.first <= this.last
}
