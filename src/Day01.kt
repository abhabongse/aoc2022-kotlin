/* Solution to Day 1: Calorie Counting
 * https://adventofcode.com/2022/day/1
 */
import utils.splitAt
import java.io.File


/**
 * An elf represents a list of items [calories] that it carries around
 */
data class Elf(val calories: List<Int>)

fun main() {
    val fileName =
//        "day01_sample.txt"
        "day01_input.txt"
    val elves = readInput(fileName)

    // Part 1: find the most calories carried by an elf
    val p1Calories = elves.maxOfOrNull { elf -> elf.calories.sum() }
    println("Part 1: $p1Calories")

    // Part 2: find the sum of calories carried by top-3 elves
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<Elf> {
    return File("inputs", fileName).readLines().splitAt { line -> line.trim().isEmpty() }
        .map { caloriesGroup -> Elf(caloriesGroup.map(String::toInt)) }.toList()
}
