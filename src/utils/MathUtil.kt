package utils

import kotlin.math.absoluteValue

object MathUtil {
    /** Computes the Greatest Common Divisor of two numbers. */
    fun gcd(a: Int, b: Int): Int {
        var a = a.absoluteValue
        var b = b.absoluteValue
        while (b != 0) {
            val r = a % b
            a = b
            b = r
        }
        return a
    }

    /** Computes the Greatest Common Divisor of multiple numbers. */
    fun gcd(numbers: Iterable<Int>): Int = numbers.reduce(MathUtil::gcd)

    /** Computes the Greatest Common Divisor of two numbers. */
    fun gcd(a: Long, b: Long): Long {
        var a = a.absoluteValue
        var b = b.absoluteValue
        while (b != 0L) {
            val r = a % b
            a = b
            b = r
        }
        return a
    }

    /** Computes the Greatest Common Divisor of multiple numbers. */
    fun gcd(numbers: Iterable<Long>): Long = numbers.reduce(MathUtil::gcd)

    /** Computes the Least Common Multiple of two numbers. */
    fun lcm(a: Int, b: Int): Int {
        var a = a.absoluteValue
        var b = b.absoluteValue
        return a / gcd(a, b) * b
    }

    /** Computes the Least Common Multiple of multiple numbers. */
    fun lcm(numbers: Iterable<Int>): Int = numbers.reduce(MathUtil::lcm)

    /** Computes the Least Common Multiple of two numbers. */
    fun lcm(a: Long, b: Long): Long {
        var a = a.absoluteValue
        var b = b.absoluteValue
        return a / gcd(a, b) * b
    }

    /** Computes the Least Common Multiple of multiple numbers. */
    fun lcm(numbers: Iterable<Long>): Long = numbers.reduce(MathUtil::lcm)
}
