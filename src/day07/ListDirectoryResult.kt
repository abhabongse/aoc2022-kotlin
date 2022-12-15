package day07

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
