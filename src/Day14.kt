data class Robot(val px: Int, val py: Int, val vx: Int, val vy: Int)

fun parseInput(input: List<String>): List<Robot> {
    return input.map { line ->
        val parts = line.split(" ")
        val position = parts[0].substringAfter("p=").removeSuffix(",").split(",").map { it.toInt() }
        val velocity = parts[1].substringAfter("v=").split(",").map { it.toInt() }
        Robot(position[0], position[1], velocity[0], velocity[1])
    }
}

fun simulateRobots(robots: List<Robot>, time: Int, width: Int, height: Int): Array<IntArray> {
    val grid = Array(height) { IntArray(width) }

    robots.forEach { robot ->
        val finalX = (robot.px + robot.vx * time) % width
        val finalY = (robot.py + robot.vy * time) % height
        val wrappedX = if (finalX < 0) finalX + width else finalX
        val wrappedY = if (finalY < 0) finalY + height else finalY
        grid[wrappedY][wrappedX]++
    }

    return grid
}

fun calculateSafetyFactor(grid: Array<IntArray>, width: Int, height: Int): Int {
    val halfWidth = width / 2
    val halfHeight = height / 2

    val quadrants = IntArray(4)

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            if (x == halfWidth || y == halfHeight) continue // Ignore robots exactly on the middle lines

            val quadrant = when {
                x < halfWidth && y < halfHeight -> 0 // Top-left
                x >= halfWidth && y < halfHeight -> 1 // Top-right
                x < halfWidth && y >= halfHeight -> 2 // Bottom-left
                else -> 3 // Bottom-right
            }
            quadrants[quadrant] += grid[y][x]
        }
    }

    return quadrants.reduce { acc, value -> acc * value }
}

fun simulateRobotsForCompactPattern(robots: List<Robot>, width: Int, height: Int, maxSeconds: Int): Int {
    for (seconds in 1..maxSeconds) {
        val grid = Array(height) { IntArray(width) }
        var valid = true

        robots.forEach { robot ->
            val nx = (robot.px + seconds * robot.vx).mod(width)
            val ny = (robot.py + seconds * robot.vy).mod(height)

            grid[ny][nx]++
            if (grid[ny][nx] > 1) {
                valid = false
            }
        }

        if (valid) {
            println("Part 2 - Compact pattern at $seconds seconds:")
            visualizeGrid(grid, width, height)
            return seconds
        }
    }

    return -1
}

fun visualizeGrid(grid: Array<IntArray>, width: Int, height: Int) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            print(if (grid[y][x] > 0) "#" else ".")
        }
        println()
    }
}

fun main() {
    val input = readInput("Day14")

    val robots = parseInput(input)
    val width = 101
    val height = 103
    val maxSeconds = 10000

    val time = 100
    val grid = simulateRobots(robots, time, width, height)
    val safetyFactor = calculateSafetyFactor(grid, width, height)
    println("Part 1: $safetyFactor")

    // Part 2: Find the fewest seconds for a compact pattern
    simulateRobotsForCompactPattern(robots, width, height, maxSeconds)

}
