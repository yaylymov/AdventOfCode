import java.util.*

data class Position(
    val x: Int,
    val y: Int,
    val direction: Int, // 0 = East, 1 = South, 2 = West, 3 = North
    val cost: Int,
    val path: List<Pair<Int, Int>>
)

fun main() {
    val input = readInput("Day16")
    val map = input.map { it.toCharArray() }

    val start = findPosition(map, 'S')
    val end = findPosition(map, 'E')

    val (minPaths, minCost) = calculateMinimumPaths(map, Pair(start.first, start.second), Pair(end.first, end.second))
    val uniqueTiles = countUniqueTiles(minPaths)

    println("Part 1: Lowest Score = $minCost")
    println("Part 2: Unique Tiles on Best Paths = ${uniqueTiles.size}")
}

fun findPosition(map: List<CharArray>, target: Char): Triple<Int, Int, Int> {
    map.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
            if (value == target) return Triple(x, y, 0)
        }
    }
    throw IllegalArgumentException("$target not found in the map")
}

// BFS implementation
fun calculateMinimumPaths(
    map: List<CharArray>,
    start: Pair<Int, Int>,
    end: Pair<Int, Int>
): Pair<List<List<Pair<Int, Int>>>, Int> {
    val directions = listOf(
        Pair(1, 0),  // East
        Pair(0, 1),  // South
        Pair(-1, 0), // West
        Pair(0, -1)  // North
    )

    val queue: Queue<Position> = LinkedList()
    val visited = mutableMapOf<String, Int>()
    val minPaths = mutableListOf<List<Pair<Int, Int>>>()
    var minCost = Int.MAX_VALUE

    queue.add(Position(start.first, start.second, 0, 0, emptyList()))

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        val currentPath = current.path + Pair(current.x, current.y)

        // Check if we reached the endpoint
        if (current.x == end.first && current.y == end.second) {
            if (current.cost < minCost) {
                minPaths.clear()
                minCost = current.cost
            }
            if (current.cost == minCost) {
                minPaths.add(currentPath)
            }
            continue
        }

        // Set the current state as visited
        val key = "${current.x}_${current.y}_${current.direction}"
        if (visited[key] != null && visited[key]!! <= current.cost) continue
        visited[key] = current.cost

        // Explore neighboring positions
        directions.forEachIndexed { directionIndex, (dx, dy) ->
            if (directionIndex == (current.direction + 2) % 4) return@forEachIndexed
            val nextX = current.x + dx
            val nextY = current.y + dy

            // Check if the move is valid
            if (nextX !in map[0].indices || nextY !in map.indices || map[nextY][nextX] == '#') return@forEachIndexed

            val newCost = current.cost + if (directionIndex == current.direction) 1 else 1001
            queue.add(Position(nextX, nextY, directionIndex, newCost, currentPath))
        }
    }
    return Pair(minPaths, minCost)
}

fun countUniqueTiles(paths: List<List<Pair<Int, Int>>>): Set<Pair<Int, Int>> {
    val uniqueTiles = mutableSetOf<Pair<Int, Int>>()
    paths.forEach { path -> uniqueTiles.addAll(path) }
    return uniqueTiles
}
