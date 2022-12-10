package utils

/**
 * Splits the given iterator into multiple chunks using the given predicate.
 * See also: [more_itertools.split_at](https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.split_at)
 *
 * Implementation Notes:
 * This function is not suitable with
 */
inline fun <T> Iterable<T>.splitAt(crossinline predicate: (T) -> Boolean): Sequence<List<T>> {
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
