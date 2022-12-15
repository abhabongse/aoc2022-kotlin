/* Solution to Day 6: Tuning Trouble
 * https://adventofcode.com/2022/day/6
 */
package day06

import java.io.File

fun main() {
    val fileName =
//        "day06_sample_a.txt"
//        "day06_sample_b.txt"
//        "day06_sample_c.txt"
//        "day06_sample_d.txt"
//        "day06_sample_e.txt"
        "day06_input.txt"
    val datastream = readInput(fileName)

    // Part 1: find the location of the first marker of length 4
    val p1FirstMarker = findFirstMarker(datastream, markerLength = 4)
    println("Part 1: $p1FirstMarker")

    // Part 2: find the location of the first marker of length 14
    val p2FirstMarker = findFirstMarker(datastream, markerLength = 14)
    println("Part 2: $p2FirstMarker")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): String {
    return File("inputs", fileName).readText().trim()
}

/**
 * Find the location of the first marker, specifically the index to the last character
 * of the first appearance substring containing unique characters.
 */
fun findFirstMarker(datastream: String, markerLength: Int): Int? {
    val counter = object {
        val frequencies: HashMap<Char, Int> = HashMap()
        var uniqueCount = 0
            private set

        fun modify(char: Char, diff: Int) {
            val oldFreq = this.frequencies.getOrDefault(char, 0)
            val newFreq = oldFreq + diff
            if (newFreq < 0) {
                throw IllegalArgumentException("new frequency for $char would decrease from $oldFreq to $newFreq")
            }
            this.frequencies[char] = newFreq
            if (oldFreq == 0 && newFreq > 0) {
                this.uniqueCount++
            }
            if (oldFreq > 0 && newFreq == 0) {
                this.uniqueCount--
            }
        }
    }

    for ((index, char) in datastream.withIndex()) {
        counter.modify(char, +1)
        if (index >= markerLength) {
            counter.modify(datastream[index - markerLength], -1)
        }
        if (counter.uniqueCount == markerLength) {
            return index + 1
        }
    }
    return null
}
