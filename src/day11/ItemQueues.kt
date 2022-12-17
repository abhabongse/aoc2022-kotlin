package day11

typealias ItemQueue = ArrayDeque<Int>

private val initialItemQueuePattern = """Starting items: (|\d+(?:, \d+)*)""".toRegex()

/**
 * Parses an input line representing the initial item queue for a monkey.
 */
fun parseInitialItemQueue(string: String): ItemQueue {
    val (items) = initialItemQueuePattern
        .matchEntire(string)
        ?.destructured
        ?: throw IllegalArgumentException("invalid format: $string")
    return items.split(", ").map { it.toInt() }.toCollection(ArrayDeque())
}
