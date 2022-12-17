package day11

/**
 * An item queue handled by a particular monkey.
 */
data class ItemQueue(val queue: ArrayDeque<Int>) {
    var dequeueCount = 0
        private set

    companion object {
        private val pattern = """Starting items: (|\d+(?:, \d+)*)""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun from(string: String): ItemQueue {
            val (items) = pattern
                .matchEntire(string)
                ?.destructured
                ?: throw IllegalArgumentException("invalid format: $string")
            val queue = items.split(", ").map { it.toInt() }.toCollection(ArrayDeque())
            return ItemQueue(queue)
        }
    }

    fun isEmpty() = this.queue.isEmpty()
    fun isNotEmpty() = !this.isEmpty()

    /** Insert an item into the queue. */
    fun enqueue(item: Int) = this.queue.addLast(item)

    /** Remove an item from the queue. */
    fun dequeue(): Int {
        this.dequeueCount++
        return this.queue.removeFirst()
    }
}
