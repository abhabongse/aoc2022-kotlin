/* Solution to Day 7: No Space Left On Device
 * https://adventofcode.com/2022/day/7
 */
package day07

import day07.Command.ChangeDirectory
import day07.Command.ListDirectoryContent
import day07.ListDirectoryResult.FileResult
import day07.ListDirectoryResult.SubDirectory
import utils.splitBefore
import java.io.File

fun main() {
    val fileName =
//        "day07_sample.txt"
        "day07_input.txt"
    val histories = readInput(fileName)
    val fileSizes = listFileSizes(histories)
    val dirSizes = computeDirSizes(fileSizes)

    // Part 1: directories with total size at most 100_000
    val p1SumTotalSize = dirSizes
        .map { (_, size) -> size }
        .filter { it <= 100_000 }
        .sum()
    println("Part 1: $p1SumTotalSize")

    // Part 2: find directory just large enough to remove so that
    // there is enough available space to system upgrade
    val currentUsage = dirSizes[Path.root]!!
    val spaceToRemove = currentUsage - 40_000_000
    val p2MatchedSize = dirSizes
        .map { (_, size) -> size }
        .filter { it >= spaceToRemove }
        .min()
    println("Part 2: $p2MatchedSize")
}

/**
 * Reads and parses input data according to the problem statement.
 */
fun readInput(fileName: String): List<History> {
    return File("inputs", fileName)
        .readLines()
        .asSequence()
        .map { it.trim() }
        .splitBefore { it.startsWith("$ ") }
        .map { lines -> History fromString lines }
        .toList()
}

/**
 * A single command history entry consisting of the unparsed [command] string
 * and the list of unparsed [results].
 */
data class History(val command: String, val results: List<String>) {
    companion object {
        infix fun fromString(lines: List<String>): History =
            History(command = lines[0].removePrefix("$ "), results = lines.drop(1))
    }
}

/**
 * A command type consisting of two subtypes:
 * - [ListDirectoryContent] for `ls` command
 * - [ChangeDirectory] for `cd` command
 */
sealed class Command {
    object ListDirectoryContent : Command()
    class ChangeDirectory(val arg: String) : Command()

    companion object {
        infix fun fromString(string: String): Command {
            val tokens = string.split("""\s+""".toRegex())
            return if (tokens[0] == "ls" && tokens.size == 1) {
                ListDirectoryContent
            } else if (tokens[0] == "cd" && tokens.size == 2) {
                ChangeDirectory(arg = tokens[1])
            } else {
                throw IllegalArgumentException("unknown command: $string")
            }
        }
    }
}

/**
 * Each line result for the [Command.ListDirectoryContent] command.
 * Two possible subtypes: a [SubDirectory] or a [FileResult].
 */
sealed class ListDirectoryResult {
    class SubDirectory(val name: String) : ListDirectoryResult()
    class FileResult(val name: String, val size: Int) : ListDirectoryResult()

    companion object {
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

/**
 * Path is represented by an immutable list of path components.
 */
@JvmInline
value class Path(private val data: List<String>) {
    companion object {
        val root = Path(emptyList())
    }

    val parent get() = Path(this.data.dropLast(1))
    operator fun plus(name: String) = Path(this.data + listOf(name))
    fun isRoot() = this == root
    fun isNotRoot() = this != root
}

/**
 * Obtains the mapping of file paths to their sizes
 * based on the list of command histories.
 */
fun listFileSizes(histories: List<History>): List<Pair<Path, Int>> {
    val fileSizes: MutableList<Pair<Path, Int>> = mutableListOf()
    var workingDir = Path.root
    for (history in histories) {
        when (val command = Command fromString history.command) {
            is ChangeDirectory -> {
                workingDir = when (command.arg) {
                    ".." -> workingDir.parent
                    "/" -> Path.root
                    else -> workingDir + command.arg
                }
            }

            is ListDirectoryContent -> {
                for (historyResult in history.results) {
                    val result = ListDirectoryResult fromString historyResult
                    if (result is FileResult) {
                        val path = workingDir + result.name
                        fileSizes.add(Pair(path, result.size))
                    }
                }
            }
        }
    }
    return fileSizes
}

/**
 * Computes the mapping of container directories to their sizes
 * based on the files sizes information returned by [listFileSizes].
 */
fun computeDirSizes(fileSizes: List<Pair<Path, Int>>): HashMap<Path, Int> {
    val dirSizes: HashMap<Path, Int> = HashMap()
    for ((path, size) in fileSizes) {
        var currPath = path
        while (currPath.isNotRoot()) {
            currPath = currPath.parent
            dirSizes[currPath] = dirSizes.getOrDefault(currPath, 0) + size
        }
    }
    return dirSizes
}
