import java.util.*

data class Node(val position: Pair<Int, Int>, val score: Int)

fun main() {
    val input = readInput("Day18")
    println("Part 1: ${part1Of18(input)}")
    println("Part 2: ${part2Of18(input)}")
}

fun part1Of18(input: List<String>): Int {
    val bytes = input.take(1024).map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Pair(x, y)
    }.toSet()

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    for (y in 0..70) {
        for (x in 0..70) {
            grid[Pair(x, y)] = if (Pair(x, y) in bytes) '#' else '.'
        }
    }
    return dijkstra(grid, 71, 71)
}

fun dijkstra(grid: Map<Pair<Int, Int>, Char>, rows: Int, cols: Int): Int {
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) // Right, Down, Left, Up
    val queue: Deque<Node> = ArrayDeque()
    val visited = mutableSetOf<Pair<Int, Int>>()

    queue.add(Node(Pair(0, 0), 0))
    visited.add(Pair(0, 0))

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        if (current.position == Pair(rows - 1, cols - 1)) {
            return current.score
        }

        for ((dx, dy) in directions) {
            val neighbor = Pair(current.position.first + dx, current.position.second + dy)

            if (neighbor !in visited && neighbor in grid && grid[neighbor] == '.') {
                visited.add(neighbor)
                queue.add(Node(neighbor, current.score + 1))
            }
        }
    }
    return -1
}

fun part2Of18(input: List<String>): String {
    val bytes = input.map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Pair(x, y)
    }
    val rows = 71
    val cols = 71

    val grid = mutableMapOf<Pair<Int, Int>, Char>().apply {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                this[Pair(x, y)] = '.'
            }
        }
    }

    for (byte in bytes) {
        grid[byte] = '#'

        if (!hasPath(grid, rows, cols)) {
            return "${byte.first},${byte.second}"
        }
    }
    return "-1,-1"
}

fun hasPath(grid: Map<Pair<Int, Int>, Char>, rows: Int, cols: Int): Boolean {
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) // Right, Down, Left, Up
    val queue: Deque<Pair<Int, Int>> = ArrayDeque()
    val visited = mutableSetOf<Pair<Int, Int>>()

    queue.add(Pair(0, 0))
    visited.add(Pair(0, 0))

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        if (current == Pair(rows - 1, cols - 1)) {
            return true
        }

        for ((dx, dy) in directions) {
            val neighbor = Pair(current.first + dx, current.second + dy)

            if (neighbor !in visited && neighbor in grid && grid[neighbor] == '.') {
                visited.add(neighbor)
                queue.add(neighbor)
            }
        }
    }
    return false
}
