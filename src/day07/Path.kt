package day07

/** Path is represented by an immutable list of path components. */
@JvmInline
value class Path(private val data: List<String>) {
    companion object {
        /** Represents root directory */
        val root = Path(emptyList())
    }

    /** Obtains the parent directory of the path. */
    val parent get() = Path(this.data.dropLast(1))

    /** Descends into a subdirectory [name]. */
    operator fun plus(name: String) = Path(this.data + listOf(name))

    /** Checks whether the path is the root. */
    fun isRoot() = this == root

    /** Checks whether the path is not the root. */
    fun isNotRoot() = !this.isRoot()
}
