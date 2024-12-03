fun main() {
    val regex = "mul\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)".toRegex()

    fun helper(input: Sequence<MatchResult>): Int {
        return input.sumOf { matchResult ->
            val (x, y) = matchResult.destructured
            x.toInt() * y.toInt()
        }
    }

    fun part1(input: String): Int {
        val result = regex.findAll(input)
        return helper(result)
    }

    fun part2(input: String): Int {
        var final = 0
        var isEnabled = true

        input.split("(?=do\\(\\)|don't\\(\\))".toRegex()).forEach { segment ->
            if (segment.contains("do()")) {
                isEnabled = true
            } else if (segment.contains("don't()")) {
                isEnabled = false
            }
            if (isEnabled) {
                val result = regex.findAll(segment)
                final += helper(result)
            }
        }
        return final
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03").joinToString { it.trim() }
    part1(input).println()
    part2(input).println()
}
