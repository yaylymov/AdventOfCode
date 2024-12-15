import kotlin.io.path.Path
import kotlin.io.path.readText

data class Scenario(
    val a: Pair<Int, Int>,
    val b: Pair<Int, Int>,
    val prize: Pair<Long, Long>
)

fun parseScenario(input: String, offset: Long = 0L): Scenario {
    val lines = input.split("\n")
    val a = lines[0].substringAfter(": ").split(", ").map { it.split("+")[1].toInt() }
    val b = lines[1].substringAfter(": ").split(", ").map { it.split("+")[1].toInt() }
    val prize = lines[2].substringAfter(": ").split(", ").map { it.split("=")[1].toLong() + offset }

    return Scenario(
        a = Pair(a[0], a[1]),
        b = Pair(b[0], b[1]),
        prize = Pair(prize[0], prize[1])
    )
}

fun solveScenario(scenario: Scenario): Long {
    val (ax, ay) = scenario.a
    val (bx, by) = scenario.b
    val (tx, ty) = scenario.prize

    val denominator = (ax.toLong() * by - ay.toLong() * bx)
    if (denominator == 0L) return 0L // No solution if determinant is 0 (parallel lines)

    val aNumerator = (tx * by - ty * bx)
    val bNumerator = (ty * ax - tx * ay)

    if (aNumerator % denominator != 0L || bNumerator % denominator != 0L) return 0L // No integer solution

    val a = aNumerator / denominator
    val b = bNumerator / denominator

    if (a < 0 || b < 0) return 0L // Non-negative presses only

    return 3L * a + b
}

fun main() {
    fun readInput1(name: String) = Path("src/$name.txt").readText().trim()
    val input = readInput1("Day13")
    val scenarios = input.split("\n\n")

    // Part 1: Solve with original coordinates
    val part1Scenarios = scenarios.map { parseScenario(it) }
    val part1Answer = part1Scenarios.sumOf { solveScenario(it) }
    println("Part 1: $part1Answer")

    // Part 2: Solve with adjusted prize coordinates
    val offset = 10000000000000L
    val part2Scenarios = scenarios.map { parseScenario(it, offset) }
    val part2Answer = part2Scenarios.sumOf { solveScenario(it) }
    println("Part 2: $part2Answer")
}
