fun main() {

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

    fun isMatch(startRow: Int, startCol: Int, dr: Int, dc: Int, rows: Int, cols: Int, grid: List<String>): Boolean {
        for (i in word.indices) {
            val nr = startRow + dr * i
            val nc = startCol + dc * i
            if (nr !in 0 until rows || nc !in 0 until cols || grid[nr][nc] != word[i]) {
                return false
            }
        }
        return true
    }

    fun part1(grid: List<String>): Int {
        val rows = grid.size
        val cols = grid[0].length
        var count = 0

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == word[0]) {
                    directions.forEach { (dr, dc) ->
                        if (isMatch(r, c, dr, dc, rows, cols, grid)) count++
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

        for (r in 0 until rows - 2) {
            for (c in 0 until cols - 2) {
                val d1 = listOf(grid[r][c], grid[r + 1][c + 1], grid[r + 2][c + 2]).joinToString("")
                val d2 = listOf(grid[r + 2][c], grid[r + 1][c + 1], grid[r][c + 2]).joinToString("")

                if ((d1 == "MAS" || d1 == "SAM") && (d2 == "MAS" || d2 == "SAM")) {
                    count++
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
