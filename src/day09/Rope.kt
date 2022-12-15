package day09

import utils.FourDirection
import utils.Vec2
import utils.accumulate
import kotlin.math.sign

/**
 * The state of a rope represented by the list of location
 * of each knot in the rope in that order from head to tail.
 */
data class Rope(val positions: List<Vec2>) {
    companion object {
        /**
         * Returns a new rope where all knots are at the point of origin.
         */
        fun atOrigin(length: Int) = Rope(List(length) { Vec2.zero })
    }

    /** Obtains the position to the head of the rope. */
    val head get() = this.positions.first()

    /** Obtains the position to the tail of the rope. */
    val tail get() = this.positions.last()

    /**
     * Moves the current rope to the next state
     * using the given direction instruction for the head knot.
     */
    infix fun transitionBy(headDirection: FourDirection): Rope {
        val phantomHead = this.head + headDirection.value * 2
        val newPositions = this.positions
            .asSequence()
            .accumulate(phantomHead) { newPrevPosition, oldCurrPosition ->
                val gradient = (newPrevPosition - oldCurrPosition).let { diff ->
                    if (diff.normMax() > 1) {
                        Vec2(diff.x.sign, diff.y.sign)
                    } else {
                        Vec2.zero
                    }
                }
                oldCurrPosition + gradient
            }
            .toList()
        return Rope(newPositions)
    }
}
