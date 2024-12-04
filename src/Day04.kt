fun main() {

    fun part1(grid: List<String>): Int {
        val word = "XMAS"
        val directions = listOf(
            Pair(0, 1),    // right
            Pair(1, 0),    // down
            Pair(1, 1),    // diagonal down-right
            Pair(1, -1),   // diagonal down-left
            Pair(0, -1),   // left
            Pair(-1, 0),   // up
            Pair(-1, -1),  // diagonal up-left
            Pair(-1, 1)    // diagonal up-right
        )

        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        fun isValid(x: Int, y: Int) = x in 0 until rows && y in 0 until cols

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == word[0]) {
                    for ((dr, dc) in directions) {
                        var match = true
                        for (i in word.indices) {
                            val nr = r + dr * i
                            val nc = c + dc * i
                            if (!isValid(nr, nc) || grid[nr][nc] != word[i]) {
                                match = false
                                break
                            }
                        }
                        if (match) count++
                    }
                }
            }
        }

        return count
    }

    fun part2(grid: List<String>): Int {
        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        fun isValid(x: Int, y: Int) = x in 0 until rows && y in 0 until cols

        for (r in 1 until rows - 1) {
            for (c in 1 until cols - 1) {
                if (grid[r][c] == 'A') {
                    // Check for "M" at four corners, with "A" in the middle
                    val topLeft = isValid(r - 1, c - 1) && grid[r - 1][c - 1] == 'M'
                    val middleLeft = isValid(r, c - 1) && grid[r][c - 1] == 'S'
                    val bottomRight = isValid(r + 1, c + 1) && grid[r + 1][c + 1] == 'M'
                    val middleRight = isValid(r, c + 1) && grid[r][c + 1] == 'S'

                    val topRight = isValid(r - 1, c + 1) && grid[r - 1][c + 1] == 'M'
                    val middleUp = isValid(r - 1, c) && grid[r - 1][c] == 'A'
                    val bottomLeft = isValid(r + 1, c - 1) && grid[r + 1][c - 1] == 'S'
                    val middleDown = isValid(r + 1, c) && grid[r + 1][c] == 'M'

                    if ((topLeft && bottomRight && middleLeft && middleRight) || (topRight && bottomLeft && middleUp && middleDown)) {
                        count++
                    }
                }
            }
        }

        return count
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
