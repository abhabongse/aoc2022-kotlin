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
    forest.populateViewingBlockHeight()
    val p1VisibleTrees = forest.iteratorRowMajor().count { (_, _, tree) -> tree.visibleFromOneDirection }
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
 * Data for each cardinal direction.
 */
data class FourDirections<T>(var north: T, var south: T, var west: T, var east: T)

/**
 * Information about the viewing block height and the viewing distance for a tree
 * toward each of the four cardinal directions.
 */
class Tree(internal val height: Int) {
    internal var blockHeight: FourDirections<Int> = FourDirections(-1, -1, -1, -1)
    internal var viewingDistance: FourDirections<Int> = FourDirections(-1, -1, -1, -1)

    val visibleFromOneDirection
        get() = this.height > this.blockHeight.north ||
                this.height > this.blockHeight.south ||
                this.height > this.blockHeight.west ||
                this.height > this.blockHeight.east

    val scenicScore
        get() = this.viewingDistance.north *
                this.viewingDistance.south *
                this.viewingDistance.west *
                this.viewingDistance.east
}

/**
 * Populates the viewing block height data for each tree in all four directions.
 */
@OptIn(ExperimentalStdlibApi::class)
fun Grid<Tree>.populateViewingBlockHeight() {
    for (i in 0..<this.numRows) {
        // From the west
        var accm = -1
        for (j in 0..<this.numCols) {
            this[i, j].blockHeight.west = accm
            accm = max(accm, this[i, j].height)
        }
        // From the east
        accm = -1
        for (j in (0..<this.numCols).reversed()) {
            this[i, j].blockHeight.east = accm
            accm = max(accm, this[i, j].height)
        }
    }
    for (j in 0..<this.numCols) {
        // From the north
        var accm = -1
        for (i in 0..<this.numRows) {
            this[i, j].blockHeight.north = accm
            accm = max(accm, this[i, j].height)
        }
        accm = -1
        for (i in (0..<this.numRows).reversed()) {
            this[i, j].blockHeight.south = accm
            accm = max(accm, this[i, j].height)
        }
    }
}
