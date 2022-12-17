package day11

import utils.otherwiseThrow

/**
 * Simple arithmetic expression to update the worry level
 * of an item handled by a particular monkey.
 */
data class ModifyAction(val left: Term, val op: MathOp, val right: Term) {
    /**
     * Represents a term in the arithmetic expression.
     */
    sealed class Term {
        /** Expression evaluation function. */
        abstract fun eval(oldItem: Long): Long

        /** Number term */
        data class Number(val value: Long) : Term() {
            override fun eval(oldItem: Long): Long = value
        }

        /** Input variable name */
        data class InputVar(val name: String) : Term() {
            init {
                (this.name == "old").otherwiseThrow { IllegalArgumentException("unknown variable name ${this.name}") }
            }

            override fun eval(oldItem: Long): Long = oldItem
        }

        companion object {
            /** Creates an object by parsing the given [string]. */
            infix fun from(string: String): Term =
                string.toLongOrNull()?.let { Number(it) } ?: InputVar(string)
        }
    }

    /**
     * Represents a binary operation symbol appeared in the arithmetic expression.
     */
    enum class MathOp(val symbol: Char) {
        PLUS('+'), TIMES('*');

        companion object {
            /** Creates an object by parsing the given [char]. */
            infix fun from(char: Char): MathOp =
                MathOp.values()
                    .firstOrNull { it.symbol == char }
                    ?: throw IllegalArgumentException("unknown op: $char")

            /** Creates an object by parsing the given [string]. */
            infix fun from(string: String): MathOp = MathOp from string.single()
        }
    }

    companion object {
        private val pattern = """Operation: new = (old|\d+) ([+*]) (old|\d+)""".toRegex()

        /** Creates an object by parsing the given [string]. */
        infix fun from(string: String): ModifyAction {
            val (left, op, right) = pattern
                .matchEntire(string)
                ?.destructured
                ?: throw IllegalArgumentException("invalid pattern: $string")
            return ModifyAction(Term from left, MathOp from op, Term from right)
        }
    }

    /**
     * Modifies the worry level of a given item according to the operation
     */
    fun modifyItem(oldItem: Long): Long {
        val left = this.left.eval(oldItem)
        val right = this.right.eval(oldItem)
        return when (this.op) {
            MathOp.PLUS -> left + right
            MathOp.TIMES -> left * right
        }
    }
}
