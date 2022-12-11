/* Solution to Day 5: Supply Stacks
 * https://adventofcode.com/2022/day/5
 */
package day05

import java.io.File

fun main() {
    val fileName =
//        "day05_sample.txt"
        "day05_input.txt"
    val (view, stackLabelOrder, rearrangeOps) = readInput(fileName)

    // Part 1: rearrange crates by CrateMover 9000
    val p1FinalView = simulateCrateMover9000(view, rearrangeOps)
    val p1DesiredCrates = stackLabelOrder
        .map { p1FinalView[it]!!.last() }
        .joinToString(separator = "")
    println("Part 1: $p1DesiredCrates")

    // Part 2: rearrange crates by CrateMover 9001
    val p2FinalView = simulateCrateMover9001(view, rearrangeOps)
    val p2DesiredCrates = stackLabelOrder
        .map { p2FinalView[it]!!.last() }
        .joinToString(separator = "")
    println("Part 2: $p2DesiredCrates")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): Input {
    val lines = File("inputs", fileName).readLines()

    // Prepare crate stacking data
    val crateStackingLineCount = lines.takeWhile { it.trim().isNotEmpty() }.count()
    val stackLabelLine = lines[crateStackingLineCount - 1]
    val crateStackingLines = lines.subList(0, crateStackingLineCount - 1).reversed()

    // Extract original crate stacking view
    val view: View = hashMapOf()
    val stackLabelOrder: MutableList<Char> = mutableListOf()
    for ((col, stackLabel) in stackLabelLine.withIndex()) {
        if (!stackLabel.isDigit()) {
            continue
        }
        val stack = crateStackingLines.map { it[col] }.takeWhile { it.isLetter() }.toList()
        view[stackLabel] = ArrayDeque(stack)
        stackLabelOrder.add(stackLabel)
    }

    // Extract rearrangement operations
    val rearrangeOps = lines
        .subList(crateStackingLineCount, lines.size)
        .dropWhile { it.trim().isEmpty() }
        .map { RearrangeOp fromString it }
        .toList()

    return Input(view, stackLabelOrder, rearrangeOps)
}

data class Input(val view: View, val stackLabelOrder: StackLabelOrder, val rearrangeOps: List<RearrangeOp>)

/**
 * A view representing the crate stacking configuration.
 */
typealias View = HashMap<Char, ArrayDeque<Char>>

/**
 * Creates a deep copy of the view.
 */
fun View.copy(): View {
    val view: View = hashMapOf()
    this.entries.forEach {
        view[it.key] = ArrayDeque(it.value)
    }
    return view
}

/**
 * A list representing the original ordering of stack labels.
 */
typealias StackLabelOrder = List<Char>

/**
 * A rearrangement operation to move the given [count] of crates
 * from the [source] stack pile to the [dest] stack pile.
 */
data class RearrangeOp(val count: Int, val source: Char, val dest: Char) {
    companion object {
        private val pattern = """move (\d+) from (\d) to (\d)""".toRegex()
        infix fun fromString(string: String): RearrangeOp {
            val (count, source, dest) = pattern.matchEntire(string.trim())
                ?.destructured
                ?: throw IllegalArgumentException("invalid input line: $string")
            return RearrangeOp(count.toInt(), source.single(), dest.single())
        }
    }
}

/**
 * Simulates CrateMover 9000: moves one crate at a time.
 * See problem statement for a thorough description of this machine.
 */
fun simulateCrateMover9000(originalView: View, rearrangeOps: List<RearrangeOp>): View {
    val view: View = originalView.copy()
    for (op in rearrangeOps) {
        repeat(op.count) {
            val crate = view[op.source]!!.removeLast()
            view[op.dest]!!.addLast(crate)
        }
    }
    return view
}

/**
 * Simulates CrateMover 9001: moves multiple crates at once.
 * See problem statement for a thorough description of this machine.
 */
fun simulateCrateMover9001(originalView: View, rearrangeOps: List<RearrangeOp>): View {
    val view: View = originalView.copy()
    val auxiliaryStack: ArrayDeque<Char> = ArrayDeque()
    for (op in rearrangeOps) {
        repeat(op.count) {
            val crate = view[op.source]!!.removeLast()
            auxiliaryStack.addLast(crate)
        }
        repeat(op.count) {
            val crate = auxiliaryStack.removeLast()
            view[op.dest]!!.addLast(crate)
        }
    }
    return view
}
