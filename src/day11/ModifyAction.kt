package day11

/**
 * Updates monkey worry level according to a simple arithmetic expression.
 */
data class ModifyAction(val left: Term, val op: MathOp, val right: Term) {
    /**
     * Represents a term in modify operation.
     */
    sealed class Term {
        data class Number(val value: Int) : Term()
        data class InputVar(val name: String) : Term()

        companion object {
            infix fun from(string: String): Term =
                string.toIntOrNull()?.let { Number(it) } ?: InputVar(string)
        }
    }

    /**
     * Represents a binary operation symbol appeared in the arithmetic expression.
     */
    enum class MathOp(val symbol: Char) {
        PLUS('+'), TIMES('*');

        companion object {
            infix fun from(value: Char): MathOp =
                MathOp.values()
                    .firstOrNull { it.symbol == value }
                    ?: throw IllegalArgumentException("unknown op: $value")

            infix fun from(value: String): MathOp = MathOp from value.single()
        }
    }

    companion object {
        val pattern = """Operation: new = (old|\d+) ([+*]) (old|\d+)""".toRegex()
        infix fun from(string: String): ModifyAction {
            val (left, op, right) = pattern
                .matchEntire(string)
                ?.destructured
                ?: throw IllegalArgumentException("invalid pattern: $string")
            return ModifyAction(Term from left, MathOp from op, Term from right)
        }
    }
}
