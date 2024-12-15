fun main() {

    fun isTrailhead(x: Int, y: Int, map: List<List<Int>>): Boolean {
        return map[x][y] == 0
    }

    fun findTrailScore(x: Int, y: Int, map: List<List<Int>>): Int {
        val rows = map.size
        val cols = map[0].size
        val visited = mutableSetOf<Pair<Int, Int>>()
        var score = 0

        fun dfs(cx: Int, cy: Int, height: Int) {
            if (cx !in 0 until rows || cy !in 0 until cols || map[cx][cy] != height || (cx to cy) in visited) {
                return
            }
            visited.add(cx to cy)
            if (height == 9) {
                score++
                return
            }

            dfs(cx - 1, cy, height + 1) // up
            dfs(cx + 1, cy, height + 1) // down
            dfs(cx, cy - 1, height + 1) // left
            dfs(cx, cy + 1, height + 1) // right
        }

        dfs(x, y, 0)
        return score
    }

    fun part1(input: List<String>): Int {
        val map = input.map { row -> row.map { it.digitToInt() } }
        var totalScore = 0

        for (x in map.indices) {
            for (y in map[0].indices) {
                if (isTrailhead(x, y, map)) {
                    totalScore += findTrailScore(x, y, map)
                }
            }
        }
        return totalScore
    }

    fun findTrailRating(x: Int, y: Int, map: List<List<Int>>): Int {
        val rows = map.size
        val cols = map[0].size
        val visitedTrails = mutableSetOf<List<Pair<Int, Int>>>()
        val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

        fun dfs(path: MutableList<Pair<Int, Int>>, cx: Int, cy: Int, height: Int) {
            if (cx !in 0 until rows || cy !in 0 until cols || map[cx][cy] != height) return

            // Add current position to the path
            path.add(Pair(cx, cy))

            // If height 9 is reached, save the trail
            if (height == 9) {
                visitedTrails.add(path.toList())
            } else {
                // Explore all 4 directions
                for ((dx, dy) in directions) {
                    dfs(path, cx + dx, cy + dy, height + 1)
                }
            }

            // Backtrack
            path.removeAt(path.size - 1)
        }

        dfs(mutableListOf(), x, y, 0)
        return visitedTrails.size
    }

    fun part2(input: List<String>): Int {
        val map = input.map { row -> row.map { it.digitToInt() } }
        var totalRating = 0

        for (x in map.indices) {
            for (y in map[0].indices) {
                if (isTrailhead(x, y, map)) {
                    totalRating += findTrailRating(x, y, map)
                }
            }
        }
        return totalRating
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
