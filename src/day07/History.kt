package day07

/**
 * A single command history entry consisting of the unparsed [command] string
 * and the list of unparsed [results].
 */
data class History(val command: String, val results: List<String>) {
    companion object {
        /** Creates an object by parsing the given [lines] of string. */
        infix fun fromString(lines: List<String>): History =
            History(command = lines[0].removePrefix("$ "), results = lines.drop(1))
    }
}
