package day11

/**
 * An item queue for a particular monkey
 * where the value reflects the worry level of such item.
 */
data class ItemQueue(var queue: ArrayDeque<Long>) {
    /** The number of times the item is popped from the queue. */
    var popFrontCount = 0
        private set

    companion object {
        private val pattern = """Starting items: (|\d+(?:,\s+\d+)*)""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun from(string: String): ItemQueue {
            val (items) = pattern
                .matchEntire(string)
                ?.destructured
                ?: throw IllegalArgumentException("invalid format: $string")
            val queue = items.split(", ").map { it.toLong() }.toCollection(ArrayDeque())
            return ItemQueue(queue)
        }
    }

    /** Check whether the queue is empty */
    fun isEmpty() = this.queue.isEmpty()

    /** Check whether the queue is not empty. */
    fun isNotEmpty() = !this.isEmpty()

    /** Insert an item into the queue. */
    fun pushBack(item: Long) = this.queue.addLast(item)

    /** Remove an item from the queue. */
    fun popFront(): Long {
        this.popFrontCount++
        return this.queue.removeFirst()
    }

    /** Reset each element by a given modulus. */
    fun resetMod(modulus: Long) {
        this.queue = this.queue.map { it % modulus }.toCollection(ArrayDeque())
    }
}

/** Clone an item queue. */
fun ItemQueue.clone() = this.queue.toCollection(ArrayDeque()).let { ItemQueue(it) }

/** Clone the entire item queues. */
fun List<ItemQueue>.clone() = this.map { it.clone() }.toList()
