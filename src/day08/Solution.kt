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
    val p1VisibleTrees = forest.iterator().count { tree -> tree.visibleFromOneDirection }
    println("Part 1: $p1VisibleTrees")

    // Part 2: tree with the best scenic score
    forest.populateViewingDistance()
    val p2BestScenicScore = forest.iterator().maxOf { tree -> tree.scenicScore }
    println("Part 2: $p2BestScenicScore")
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
 * Populates the viewing block height data for each tree in all four directions.
 */
@OptIn(ExperimentalStdlibApi::class)
fun Grid<Tree>.populateViewingBlockHeight() {
    for (r in 0..<this.numRows) {
        var maxFromWest = -1  // maintains max height starting from west
        for (c in 0..<this.numCols) {
            this[r, c].viewingBlockHeight.west = maxFromWest
            maxFromWest = max(maxFromWest, this[r, c].height)
        }
        var maxFromEast = -1 // maintains max height starting from east
        for (c in (0..<this.numCols).reversed()) {
            this[r, c].viewingBlockHeight.east = maxFromEast
            maxFromEast = max(maxFromEast, this[r, c].height)
        }
    }
    for (c in 0..<this.numCols) {
        var maxFromNorth = -1 // maintains max height starting from north
        for (r in 0..<this.numRows) {
            this[r, c].viewingBlockHeight.north = maxFromNorth
            maxFromNorth = max(maxFromNorth, this[r, c].height)
        }
        var maxFromSouth = -1 // maintains max height starting from south
        for (r in (0..<this.numRows).reversed()) {
            this[r, c].viewingBlockHeight.south = maxFromSouth
            maxFromSouth = max(maxFromSouth, this[r, c].height)
        }
    }
}

/**
 * Populates the viewing distance data for each tree in all four directions.
 *
 * Implementation Details:
 * e.g. indexForMaxHeight\[h] = the most recent index whose height is at least h
 *      (initialized to the index indicating the edge of the forest)
 */
@OptIn(ExperimentalStdlibApi::class)
fun Grid<Tree>.populateViewingDistance() {
    for (r in 0..<this.numRows) {
        run { // Looking westward
            val indexForMaxHeight = (0..9).map { 0 }.toCollection(ArrayList())
            for (c in 0..<this.numCols) {
                val height = this[r, c].height
                this[r, c].viewingDistance.west = c - indexForMaxHeight[height]
                (0..height).forEach { indexForMaxHeight[it] = c }
            }
        }
        run { // Looking eastward
            val indexForMaxHeight = (0..9).map { this.numCols - 1 }.toCollection(ArrayList())
            for (c in (0..<this.numCols).reversed()) {
                val height = this[r, c].height
                this[r, c].viewingDistance.east = indexForMaxHeight[height] - c
                (0..height).forEach { indexForMaxHeight[it] = c }
            }
        }
    }
    for (c in 0..<this.numCols) {
        run { // Looking northward
            val indexForMaxHeight = (0..9).map { 0 }.toCollection(ArrayList())
            for (r in 0..<this.numRows) {
                val height = this[r, c].height
                this[r, c].viewingDistance.north = r - indexForMaxHeight[height]
                (0..height).forEach { indexForMaxHeight[it] = r }
            }
        }
        run { // Looking southward
            val indexForMaxHeight = (0..9).map { this.numRows - 1 }.toCollection(ArrayList())
            for (r in (0..<this.numRows).reversed()) {
                val height = this[r, c].height
                this[r, c].viewingDistance.south = indexForMaxHeight[height] - r
                (0..height).forEach { indexForMaxHeight[it] = r }
            }
        }
    }
}
