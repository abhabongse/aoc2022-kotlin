package utils

/**
 * Turns the Boolean value of `true` into the value generates from the given body,
 * or other just use null instead.
 */
inline fun <T> Boolean.thenOrNull(crossinline valueInit: () -> T?): T? = if (this) {
    valueInit()
} else {
    null
}

/**
 * Debug prints items from a give list
 */
fun <T> List<T>.toDebugString(): String =
    this.let { list ->
        StringBuilder().apply {
            this.append("${list::class.simpleName} with ${list.size} elements\n")
            for ((index, element) in list.withIndex()) {
                this.append("  $index: $element\n")
            }
        }.toString()
    }
