package day10

import utils.thenOrNull

/** CPU Instruction */
sealed class CpuInstruction(val numCycles: Int) {

    /** Represents noop instruction (do nothing) */
    class Noop : CpuInstruction(1) {
        companion object {
            /** Creates an object by parsing the given [string]. */
            infix fun fromString(string: String): Noop? =
                (string.trim() == "noop").thenOrNull { Noop() }
        }

        override fun toString(): String {
            return "Noop()"
        }
    }

    /** Represents adding the literal value to the register X */
    data class AddX(val value: Int) : CpuInstruction(2) {
        companion object {
            val pattern = """addx (-?\d+)""".toRegex()

            /** Creates an object by parsing the given [string]. */
            infix fun fromString(string: String): AddX? =
                pattern.matchEntire(string.trim())
                    ?.destructured
                    ?.let { (value) -> AddX(value.toInt()) }
        }
    }

    companion object {
        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): CpuInstruction =
            (Noop fromString string)
                ?: (AddX fromString string)
                ?: throw IllegalArgumentException("unknown instruction: $string")
    }
}
