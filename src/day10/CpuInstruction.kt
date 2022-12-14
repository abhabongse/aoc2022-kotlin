package day10

import utils.thenOrNull

/**
 * CPU Instruction
 */
sealed class CpuInstruction {
    /**
     * Represents noop instruction (do nothing)
     */
    class Noop : CpuInstruction() {
        companion object {
            /**
             * Creates an object by parsing the given [string].
             */
            infix fun from(string: String): Noop? =
                (string.trim() == "noop").thenOrNull { Noop() }
        }

        override fun toString(): String {
            return "Noop()"
        }
    }

    /**
     * Represents adding the literal value to the register X
     */
    data class AddX(val value: Int) : CpuInstruction() {
        companion object {
            private val pattern = """addx (-?\d+)""".toRegex()

            /**
             * Creates an object by parsing the given [string].
             */
            infix fun from(string: String): AddX? =
                pattern.matchEntire(string.trim())
                    ?.destructured
                    ?.let { (value) -> AddX(value.toInt()) }
        }
    }

    companion object {
        /**
         * Creates an object by parsing the given [string].
         */
        infix fun from(string: String): CpuInstruction =
            (Noop from string)
                ?: (AddX from string)
                ?: throw IllegalArgumentException("unknown instruction: $string")
    }
}
