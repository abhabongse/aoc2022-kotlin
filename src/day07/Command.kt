package day07

import day07.Command.ChangeDirectory
import day07.Command.ListDirectoryContent
import day07.Command.ListDirectoryContent.ListDirectoryResult.FileResult
import day07.Command.ListDirectoryContent.ListDirectoryResult.SubDirectory
import utils.thenOrNull

/**
 * A command type consisting of two subtypes:
 * - [ListDirectoryContent] for `ls` command
 * - [ChangeDirectory] for `cd` command
 */
sealed class Command {
    class ListDirectoryContent : Command() {
        companion object {
            /** Creates an object by parsing the given [string]. */
            infix fun fromString(string: String): ListDirectoryContent? =
                (string.trim() == "ls").thenOrNull { ListDirectoryContent() }
        }

        /**
         * Each line result for the [Command.ListDirectoryContent] command.
         * Two possible subtypes: a [SubDirectory] or a [FileResult].
         */
        sealed class ListDirectoryResult {
            class SubDirectory(val name: String) : ListDirectoryResult()
            class FileResult(val name: String, val size: Int) : ListDirectoryResult()

            companion object {
                /**
                 * Creates an object by parsing the given [string].
                 */
                infix fun fromString(string: String): ListDirectoryResult {
                    val tokens = string.split("""\s+""".toRegex())
                    return if (tokens.size != 2) {
                        throw IllegalArgumentException("unknown command: $string")
                    } else if (tokens[0] == "dir") {
                        SubDirectory(name = tokens[1])
                    } else {
                        FileResult(name = tokens[1], size = tokens[0].toInt())
                    }
                }
            }
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
