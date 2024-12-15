fun main() {

    fun parseInput(input: String): List<CharArray> {
        return input.trim().lines().map { it.toCharArray() }
    }

    val directions = listOf(
        Pair(-1, 0),  // North
        Pair(0, 1),   // East
        Pair(1, 0),   // South
        Pair(0, -1)   // West
    )

    fun isWithinBounds(y: Int, x: Int, board: List<CharArray>): Boolean {
        return y in board.indices && x in board[0].indices
    }


    fun simulateGuardPath(board: List<CharArray>, startY: Int, startX: Int): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()
        var currentY = startY
        var currentX = startX
        var directionIndex = 0

        while (true) {
            visited.add(currentY to currentX)
            val (dy, dx) = directions[directionIndex]

            if (!isWithinBounds(currentY + dy, currentX + dx, board)) {
                break
            }

            if (board[currentY + dy][currentX + dx] == '#') {
                directionIndex = (directionIndex + 1) % 4
            } else {
                currentY += dy
                currentX += dx
            }
        }

        return visited.size
    }

    fun simulateLoopCheck(board: List<CharArray>, startY: Int, startX: Int): Boolean {
        val visited = mutableSetOf<Triple<Int, Int, Int>>()
        var currentY = startY
        var currentX = startX
        var directionIndex = 0

        while (true) {
            val state = Triple(currentY, currentX, directionIndex)
            if (state in visited) return true
            visited.add(state)

            val (dy, dx) = directions[directionIndex]

            if (!isWithinBounds(currentY + dy, currentX + dx, board)) {
                break
            }

            if (board[currentY + dy][currentX + dx] == '#') {
                directionIndex = (directionIndex + 1) % 4
            } else {
                currentY += dy
                currentX += dx
            }
        }

        return false
    }

    fun part1(input: List<String>): Int {
        val board = parseInput(input.joinToString("\n"))
        val startY = board.indexOfFirst { row -> '^' in row }
        val startX = board[startY].indexOf('^')
        return simulateGuardPath(board, startY, startX)
    }

    fun part2(input: List<String>): Int {
        val board = parseInput(input.joinToString("\n")).map { it.copyOf() }
        val startY = board.indexOfFirst { row -> '^' in row }
        val startX = board[startY].indexOf('^')
        var loopCount = 0

        for (y in board.indices) {
            for (x in board[0].indices) {
                if (board[y][x] == '.') {
                    board[y][x] = '#'
                    if (simulateLoopCheck(board, startY, startX)) {
                        loopCount++
                    }
                    board[y][x] = '.'
                }
            }
        }
        return loopCount
    }

    val input = readInput("Day06")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
