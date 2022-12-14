package utils

/**
 * Two-dimensional grid implementation with map
 */
class Grid<T> private constructor(val numRows: Int, val numCols: Int, val data: HashMap<GridIndex, T>) {
    init {
        if (this.numRows <= 0) {
            throw IllegalArgumentException("invalid number of rows ${this.numRows}")
        }
        if (this.numCols <= 0) {
            throw IllegalArgumentException("invalid number of columns ${this.numCols}")
        }
    }

    operator fun get(index: GridIndex): T {
        if (!this.indexIsInBounds(index)) {
            throw IllegalArgumentException("index out of range: $index")
        }
        return this.data[index] ?: throw NoSuchElementException("value not set for index: $index")
    }

    operator fun set(index: GridIndex, value: T) {
        if (!this.indexIsInBounds(index)) {
            throw IllegalArgumentException("index out of range: $index")
        }
        this[index] = value
    }

    operator fun get(i: Int, j: Int): T = this[GridIndex(i, j)]
    operator fun set(i: Int, j: Int, value: T) {
        this[GridIndex(i, j)] = value
    }

    override fun toString(): String {
        return "FixedGrid(numRows=${this.numRows}, numCols=${this.numCols}, data=${this.data})"
    }

    /**
     * Special method to display grid with nice formatting.
     */
    fun toDebugString(): String =
        this.let { grid ->
            StringBuilder().apply {
                this.append("FixedGrid of ${grid.numRows} rows and ${grid.numCols} columns:\n")
                for (i in 0 until grid.numRows) {
                    for (j in 0 until grid.numCols) {
                        this.append("  ${grid[i, j]}")
                    }
                    this.append("\n")
                }
            }.toString()
        }


    /**
     * Generates a sequence of grid items in the specified order.
     * @param [axisMajor] Whether to use [GridAxisOrdering.ROW_MAJOR] or [GridAxisOrdering.COLUMN_MAJOR] ordering
     * @param [rowDirection] Whether to traverse each row in [GridDirectionOrdering.ASCENDING] or [GridDirectionOrdering.DESCENDING] order
     * @param [columnDirection] Whether to traverse each column in [GridDirectionOrdering.ASCENDING] or [GridDirectionOrdering.DESCENDING] order
     */
    fun iterator(
        axisMajor: GridAxisOrdering = GridAxisOrdering.ROW_MAJOR,
        rowDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
        columnDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
    ): Sequence<T> {
        return gridIndexSequence(this.numRows, this.numCols, axisMajor, rowDirection, columnDirection)
            .map { this[it] }
    }

    /**
     * Generates a sequence of grid items along with indices in the specified order
     * @param [axisMajor] Whether to use [GridAxisOrdering.ROW_MAJOR] or [GridAxisOrdering.COLUMN_MAJOR] ordering
     * @param [rowDirection] Whether to traverse each row in [GridDirectionOrdering.ASCENDING] or [GridDirectionOrdering.DESCENDING] order
     * @param [columnDirection] Whether to traverse each column in [GridDirectionOrdering.ASCENDING] or [GridDirectionOrdering.DESCENDING] order
     */
    fun iteratorWithIndex(
        axisMajor: GridAxisOrdering = GridAxisOrdering.ROW_MAJOR,
        rowDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
        columnDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
    ): Sequence<GridIndexedValue<T>> {
        return gridIndexSequence(this.numRows, this.numCols, axisMajor, rowDirection, columnDirection)
            .map { GridIndexedValue(it, this[it]) }
    }

    /**
     * Whether the given grid index lies within bounds of the grid
     */
    fun indexIsInBounds(index: GridIndex): Boolean {
        return index.r in 0 until this.numRows && index.c in 0 until this.numCols
    }

    companion object {
        /**
         * Constructs a grid of the given size with the default value initializer
         */
        operator fun <T> invoke(numRows: Int, numCols: Int, init: (Int, Int) -> T): Grid<T> {
            val data: HashMap<GridIndex, T> = HashMap()
            gridIndexSequence(numRows, numCols).forEach { index ->
                data[index] = init(index.r, index.c)
            }
            return Grid(numRows, numCols, data)
        }

        /**
         * Construct a grid based on the given list of lists.
         */
        operator fun <T> invoke(data: List<List<T>>): Grid<T> {
            val numRows = data.size
            if (numRows <= 0) {
                throw IllegalArgumentException("empty rows")
            }
            val numCols = data.first().size
            if (numCols <= 0) {
                throw IllegalArgumentException("empty columns in first row")
            }
            if (data.any { it.size != numCols }) {
                throw IllegalArgumentException("unmatched number of columns in all rows")
            }
            val rebuiltData: HashMap<GridIndex, T> = HashMap()
            gridIndexSequence(numRows, numCols).forEach { index ->
                rebuiltData[index] = data[index.r][index.c]
            }
            return Grid(numRows, numCols, rebuiltData)
        }
    }
}

/**
 * Zero-indexed position within a grid
 */
data class GridIndex(val r: Int, val c: Int)

/**
 * Zero-indexed position within a grid, with associated value
 */
data class GridIndexedValue<T>(val r: Int, val c: Int, val value: T) {
    constructor(index: GridIndex, value: T) : this(index.r, index.c, value)
}

/**
 * Axis-ordering when iterating over a grid
 */
enum class GridAxisOrdering {
    ROW_MAJOR, COLUMN_MAJOR;
}

/**
 * Direction ordering for each axis when iterating over a grid
 */
enum class GridDirectionOrdering {
    ASCENDING, DESCENDING;
}

/**
 * Generates a sequence of zero-indexed position within a grid
 *
 * TODO: turns this into an iterable class
 */
fun gridIndexSequence(
    numRows: Int,
    numCols: Int,
    axisMajor: GridAxisOrdering = GridAxisOrdering.ROW_MAJOR,
    rowDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
    columnDirection: GridDirectionOrdering = GridDirectionOrdering.ASCENDING,
): Sequence<GridIndex> = sequence {
    val rowIteration = when (rowDirection) {
        GridDirectionOrdering.ASCENDING -> (0 until numRows)
        GridDirectionOrdering.DESCENDING -> (0 until numRows).reversed()
    }
    val columnIteration = when (columnDirection) {
        GridDirectionOrdering.ASCENDING -> (0 until numCols)
        GridDirectionOrdering.DESCENDING -> (0 until numCols).reversed()
    }
    when (axisMajor) {
        GridAxisOrdering.ROW_MAJOR -> {
            rowIteration.forEach { r -> columnIteration.forEach { c -> this.yield(GridIndex(r, c)) } }
        }

        GridAxisOrdering.COLUMN_MAJOR -> {
            columnIteration.forEach { c -> rowIteration.forEach { r -> this.yield(GridIndex(r, c)) } }
        }
    }
}
