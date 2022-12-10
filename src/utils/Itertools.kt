package utils

import java.util.*

/**
 * Find [n] smallest items from the sequence.
 * The result is returned in the sorted descending order.
 */
fun <T : Comparable<T>> Sequence<T>.smallest(n: Int): List<T> {
    return this.largestWith(n, reverseOrder())
}

/**
 * Find [n] largest items from the sequence.
 * The result is returned in the sorted descending order.
 */
fun <T : Comparable<T>> Sequence<T>.largest(n: Int): List<T> {
    return this.largestWith(n, naturalOrder())
}

/**
 * Find [n] smallest items from the sequence according to the provided key [selector].
 * The result is returned in the sorted order starting from the largest item.
 */
fun <T> Sequence<T>.smallestBy(n: Int, selector: (T) -> Comparable<*>): List<T> {
    return this.largestWith(n, compareByDescending(selector))
}

/**
 * Find [n] largest items from the sequence according to the provided key [selector].
 * The result is returned in the sorted order starting from the largest item.
 */
fun <T> Sequence<T>.largestBy(n: Int, selector: (T) -> Comparable<*>): List<T> {
    return this.largestWith(n, compareBy(selector))
}

/**
 * Finds [n] largest items from the sequence according to the provided [comparator]
 * The result is returned in the sorted order starting from the largest item.
 */
private fun <T> Sequence<T>.smallestWith(n: Int, comparator: Comparator<T>): List<T> {
    return this.largestWith(n, comparator.reversed())
}

/**
 * Finds [n] largest items from the sequence according to the provided [comparator]
 * The result is returned in the sorted order starting from the largest item.
 */
private fun <T> Sequence<T>.largestWith(n: Int, comparator: Comparator<T>): List<T> {
    val heap: PriorityQueue<T> = PriorityQueue(comparator)
    val iterator = this.iterator()
    for (item in iterator) {
        heap.add(item)
        while (heap.size > n) {
            heap.remove()
        }
    }
    val result: ArrayList<T> = arrayListOf()
    while (heap.isNotEmpty()) {
        result.add(heap.remove())
    }
    return result.asReversed()
}

/**
 * Splits the given iterator into multiple chunks using the given predicate.
 * See also: [more_itertools.split_at](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.split_at)
 *
 * Implementation Notes:
 * This function is not suitable with
 */
inline fun <T> Sequence<T>.splitAt(crossinline predicate: (T) -> Boolean): Sequence<List<T>> {
    val iterator = this.iterator()
    return sequence {
        var accmList: ArrayList<T> = arrayListOf()
        for (line in iterator) {
            if (predicate(line)) {
                this.yield(accmList)
                accmList = arrayListOf()
            } else {
                accmList.add(line)
            }
        }
        if (accmList.isNotEmpty()) {
            this.yield(accmList)
        }
    }
}
