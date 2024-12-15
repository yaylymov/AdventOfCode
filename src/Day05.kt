import kotlin.io.path.Path
import kotlin.io.path.readText

fun getOrderingRules(grid: List<String>): List<Pair<Int, Int>> {
    return grid[0].split("\n").map {
        val (a, b) = it.split("|").map(String::toInt)
        a to b
    }
}

fun isUpdateInOrder(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    val indexMap = update.withIndex().associate { it.value to it.index }
    for ((a, b) in rules) {
        if (a in indexMap && b in indexMap && indexMap[a]!! > indexMap[b]!!) {
            return false
        }
    }
    return true
}

fun part1(grid: List<String>): Int {
    // Split input into rules and updates
    val orderingRules = getOrderingRules(grid)
    val updates = grid[1].split("\n").map { it.split(",").map(String::toInt) }


    var middlePagesSum = 0
    for (update in updates) {
        if (isUpdateInOrder(update, orderingRules)) {
            val middleIndex = update.size / 2
            middlePagesSum += update[middleIndex]
        }
    }

    return middlePagesSum
}

fun part2(grid: List<String>): Int {
    val orderingRules = getOrderingRules(grid)
    val updates = grid[1].lines().map { it.split(",").map(String::toInt) }

    fun correctOrder(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val sortedUpdate = update.toMutableList()
        sortedUpdate.sortWith { a, b ->
            when {
                rules.any { it.first == a && it.second == b } -> -1
                rules.any { it.first == b && it.second == a } -> 1
                else -> 0
            }
        }
        return sortedUpdate
    }

    var middlePagesSum = 0
    for (update in updates) {
        if (!isUpdateInOrder(update, orderingRules)) {
            val correctedUpdate = correctOrder(update, orderingRules)
            val middleIndex = correctedUpdate.size / 2
            middlePagesSum += correctedUpdate[middleIndex]
        }
    }

    return middlePagesSum
}

fun main() {
    fun readInput1(name: String) = Path("src/$name.txt").readText().split("\n\n")
    val input = readInput1("Day05")
    part1(input).println()
    part2(input).println()
}
