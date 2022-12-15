package day11

/**
 * Updates monkey worry level according to a simple arithmetic expression.
 */
data class ModifyOperation(val left: Term, val op: MathOp, val right: Term) {
    sealed class Term {
    }

    /** Represents a binary operation symbol appeared in the arithmetic expression. */
    enum class MathOp(val symbol: Char) {
        PLUS('+'), TIMES('*');
    }
}
