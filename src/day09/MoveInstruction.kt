package day09

import utils.FourDirection

/**
 * Move instructions as appeared in input
 */
data class MoveInstruction(val direction: FourDirection, val count: Int) {
    companion object {
        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): MoveInstruction {
            val (dir, cnt) = string.trim().split("""\s+""".toRegex())
            val direction = when (dir.single()) {
                'U' -> FourDirection.NORTH
                'D' -> FourDirection.SOUTH
                'L' -> FourDirection.WEST
                'R' -> FourDirection.EAST
                else -> throw IllegalArgumentException("unknown direction: $dir")
            }
            val count = cnt.toInt()
            return MoveInstruction(direction, count)
        }
    }

    /**
     * Produces a sequence of individual moves of the same [direction]
     * for the [count] number of times.
     */
    fun iterator(): Sequence<FourDirection> {
        val instr = this
        return sequence {
            repeat(instr.count) {
                this.yield(instr.direction)
            }
        }
    }
}
