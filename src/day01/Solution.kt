/* Solution to Day 1: Calorie Counting
 * https://adventofcode.com/2022/day/1
 */
package day01

import utils.largest
import utils.splitAt
import java.io.File

fun main() {
    val fileName =
//        "day01_sample.txt"
        "day01_input.txt"
    val elves = readInput(fileName)

    // Part 1: find the most calories carried by an elf
    val p1Calories = elves.maxOfOrNull { elf -> elf.calories.sum() }
    println("Part 1: $p1Calories")

    // Part 2: find the sum of calories carried by top-3 elves
    val p2Calories = elves
        .map { elf -> elf.calories.sum() }
        .asSequence()
        .largest(3)
        .sum()
    println("Part 2: $p2Calories")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<Elf> {
    return File("inputs", fileName)
        .readLines()
        .asSequence()
        .splitAt { line -> line.trim().isEmpty() }
        .map { caloriesGroup -> Elf(caloriesGroup.map(String::toInt)) }
        .toList()
}
