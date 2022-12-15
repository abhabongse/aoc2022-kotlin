package day07

import day07.Command.ChangeDirectory
import day07.Command.ListDirectoryContent
import utils.thenOrNull

/**
 * A command type consisting of two subtypes:
 * - [ListDirectoryContent] for `ls` command
 * - [ChangeDirectory] for `cd` command
 */
sealed class Command {
    class ListDirectoryContent() : Command() {
        companion object {
            /** Creates an object by parsing the given [string]. */
            infix fun fromString(string: String): ListDirectoryContent? =
                (string.trim() == "ls").thenOrNull { ListDirectoryContent() }
        }
    }

    data class ChangeDirectory(val arg: String) : Command() {
        companion object {
            val pattern = """cd (\S+)""".toRegex()

            /** Creates an object by parsing the given [string]. */
            infix fun fromString(string: String): ChangeDirectory? =
                pattern.matchEntire(string.trim())
                    ?.destructured
                    ?.let { (arg) -> ChangeDirectory(arg) }
        }
    }

    companion object {
        /** Creates an object by parsing the given [string]. */
        infix fun fromString(string: String): Command =
            (ListDirectoryContent fromString string)
                ?: (ChangeDirectory fromString string)
                ?: throw IllegalArgumentException("unknown command: $string")
    }
}
