fun main() {

    fun isSafe(report: List<Int>): Boolean {
        if (report.size < 2) return true

        val isIncreasing = report.zipWithNext().all { (a, b) -> b > a && (b - a) in 1..3 }
        val isDecreasing = report.zipWithNext().all { (a, b) -> b < a && (a - b) in 1..3 }

        return isIncreasing || isDecreasing
    }

    fun part1(input: List<String>): Int {
        val result = input.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafe(levels)
        }
        return result
    }

    fun isSafeWithDampener(report: List<Int>): Boolean {
        if (isSafe(report)) return true

        for (i in report.indices) {
            val newList = report.toMutableList().apply { removeAt(i) }
            if (isSafe(newList)) return true
        }

        return false
    }

    fun part2(input: List<String>): Int {
        val result = input.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafeWithDampener(levels)
        }
        return result
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
