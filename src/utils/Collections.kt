package utils

/**
 * Two-dimensional grid implementation
 */
class Grid<T> private constructor(private val data: ArrayList<ArrayList<T>>) {
    val numRows: Int
        get() = this.data.size
    val numCols: Int
        get() = this.data.first().size

    operator fun get(i: Int, j: Int): T = this.data[i][j]
    operator fun set(i: Int, j: Int, value: T) {
        this.data[i][j] = value
    }

    override fun toString(): String {
        return this.data.toString()
    }

    fun iteratorRowMajor(): Sequence<GridIndexedValue<T>> {
        val grid = this
        return sequence {
            for (i in 0 until grid.numRows) {
                for (j in 0 until grid.numCols) {
                    this.yield(GridIndexedValue(i, j, grid.data[i][j]))
                }
            }
        }
    }

    init {
        if (this.numRows <= 0) {
            throw IllegalArgumentException("invalid number of rows ${this.numRows}")
        }
        if (this.numCols <= 0) {
            throw IllegalArgumentException("invalid number of columns ${this.numCols}")
        }
        if (this.data.any { it.size != this.numCols }) {
            throw IllegalArgumentException("malformed data shape")
        }
    }

    companion object {
        /**
         * Constructs a grid of the given size with the default value initializer
         */
        operator fun <T> invoke(numRows: Int, numCols: Int, init: (Int, Int) -> T): Grid<T> {
            if (numRows <= 0) {
                throw IllegalArgumentException("invalid number of rows $numRows")
            }
            if (numCols <= 0) {
                throw IllegalArgumentException("invalid number of columns $numCols")
            }
            val data: ArrayList<ArrayList<T>> = (0 until numRows).map { i ->
                (0 until numCols).map { j -> init(i, j) }.toCollection(ArrayList())
            }.toCollection(ArrayList())
            return Grid(data)
        }

        /**
         * Construct a grid based on the given list of lists.
         */
        operator fun <T> invoke(data: List<List<T>>): Grid<T> =
            Grid(data.asSequence().map { it.toCollection(ArrayList()) }.toCollection(ArrayList()))
    }
}

data class GridIndexedValue<T>(val i: Int, val j: Int, val value: T)
