/* Solution to Day 8: Treetop Tree House
 * https://adventofcode.com/2022/day/8
 */
package day08

import utils.Grid
import java.io.File
import kotlin.math.max

fun main() {
    val fileName =
//        "day08_sample.txt"
        "day08_input.txt"
    val forest = readInput(fileName)

    // Part 1: visible trees from outside the grid
    for (i in 0 until forest.numRows) {
        var accm = -1
        for (j in 0 until forest.numCols) {
            forest[i, j].westBlock = accm
            accm = max(accm, forest[i, j].height)
        }
        accm = -1
        for (j in (0 until forest.numCols).reversed()) {
            forest[i, j].eastBlock = accm
            accm = max(accm, forest[i, j].height)
        }
    }
    for (j in 0 until forest.numCols) {
        var accm = -1
        for (i in 0 until forest.numRows) {
            forest[i, j].northBlock = accm
            accm = max(accm, forest[i, j].height)
        }
        accm = -1
        for (i in (0 until forest.numRows).reversed()) {
            forest[i, j].southBlock = accm
            accm = max(accm, forest[i, j].height)
        }
    }
    val p1VisibleTrees = forest.iteratorRowMajor().count { (_, _, tree) -> tree.visible }
    println("Part 1: $p1VisibleTrees")

    // Part 2:
    val p2MatchedSize = 0
    println("Part 2: $p2MatchedSize")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): Grid<Tree> {
    val data = File("inputs", fileName)
        .readLines()
        .map { line -> line.trim().map { Tree(it.digitToInt()) }.toList() }
    return Grid(data)
}

/**
 * Tree information in the forest grid.
 */
data class Tree(
    val height: Int,
    var northBlock: Int = -1,
    var southBlock: Int = -1,
    var westBlock: Int = -1,
    var eastBlock: Int = -1,
) {
    val visible
        get() = this.height > this.northBlock ||
                this.height > this.southBlock ||
                this.height > this.westBlock ||
                this.height > this.eastBlock
}
