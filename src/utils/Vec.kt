package utils

/**
 * Standard two-dimensional vector implementation
 */
class Vec2(val x: Int, val y: Int) {
    operator fun unaryPlus(): Vec2 = Vec2(+this.x, +this.y)
    operator fun unaryMinus(): Vec2 = Vec2(-this.x, -this.y)
    operator fun plus(other: Vec2): Vec2 = Vec2(this.x + other.x, this.y + other.y)
    operator fun minus(other: Vec2): Vec2 = Vec2(this.x - other.x, this.y - other.y)
    operator fun times(other: Int): Vec2 = Vec2(this.x * other, this.y * other)
    override fun toString(): String = "(${this.x}, ${this.y})"

    fun isZero(): Boolean = this == zero
    fun isNonZero(): Boolean = this != zero

    companion object {
        val zero = Vec2(0, 0)
    }
}

operator fun Int.times(other: Vec2): Vec2 = other * this


/**
 * Orthogonal adjacent integral positions (four cardinal directions)
 */
enum class FourDirection(value: Vec2) {
    EAST(Vec2(1, 0)),
    NORTH(Vec2(0, -1)),
    WEST(Vec2(-1, 0)),
    SOUTH(Vec2(0, 1)),
}

/**
 * Omnidirectional adjacent integral positions (eight cardinal directions)
 */
enum class EightDirection(value: Vec2) {
    EAST(Vec2(1, 0)),
    NORTH_EAST(Vec2(1, -1)),
    NORTH(Vec2(0, -1)),
    NORTH_WEST(Vec2(-1, -1)),
    WEST(Vec2(-1, 0)),
    SOUTH_WEST(Vec2(-1, 1)),
    SOUTH(Vec2(0, 1)),
    SOUTH_EAST(Vec2(1, 1)),
}
