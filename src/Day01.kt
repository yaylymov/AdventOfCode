fun main() {

    fun part1(input: List<String>): Int {
        val (left, right) = input.map { line ->
            val first = line.substringBefore("  ").toInt()
            val second = line.substringAfterLast("  ").toInt()
            first to second
        }.unzip()

        val sortedLeft = left.sorted()
        val sortedRight = right.sorted()

        val result = sortedLeft.zip(sortedRight).sumOf { (left, right) -> kotlin.math.abs(left - right) }
        return result
    }

    fun part2(input: List<String>): Long {
        val (left, right) = input.map { line ->
            line.split(Regex("\\s+")).let {
                it[0].toLong() to it[1].toLong()
            }
        }.unzip()

        return left.fold(0L) { acc, num ->
            acc + num * right.groupingBy { it }.eachCount().getOrDefault(num, 0)
        }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
