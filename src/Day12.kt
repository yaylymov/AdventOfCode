import java.lang.Math.floorMod
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {

    fun parseGrid(input: List<String>): Array<CharArray> {
        return input.map { it.toCharArray() }.toTypedArray()
    }

    fun calculateAreaAndPerimeter(
        input2: Array<CharArray>,
        visited: Array<BooleanArray>,
        x: Int,
        y: Int,
        plant: Char
    ): Pair<Int, Int> {
        val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(x, y))
        visited[x][y] = true

        var area = 0
        var perimeter = 0

        while (queue.isNotEmpty()) {
            val (currentX, currentY) = queue.removeFirst()
            area++

            for ((dx, dy) in directions) {
                val newX = currentX + dx
                val newY = currentY + dy
                if (newX !in input2.indices || newY !in input2[0].indices || input2[newX][newY] != plant) {
                    // Edge contributes to perimeter
                    perimeter++
                } else if (!visited[newX][newY]) {
                    visited[newX][newY] = true
                    queue.add(Pair(newX, newY))
                }
            }
        }

        return Pair(area, perimeter)
    }

    fun part1(input: List<String>): Int {
        val input2 = parseGrid(input)
        val visited = Array(input2.size) { BooleanArray(input2[0].size) }

        var totalPrice = 0

        for (x in input2.indices) {
            for (y in input2[0].indices) {
                if (!visited[x][y]) {
                    val plant = input2[x][y]
                    val (area, perimeter) = calculateAreaAndPerimeter(input2, visited, x, y, plant)
                    totalPrice += area * perimeter
                }
            }
        }

        return totalPrice
    }

    val input2 = readInput("Day12").map { it.toCharArray().toMutableList() }.toMutableList()
    val input2Copy = input2.map { it.toMutableList() }

    // Directions for navigation: North, East, South, West
    val rowDelta = listOf(-1, 0, 1, 0)
    val colDelta = listOf(0, 1, 0, -1)

    fun isWithinBounds(row: Int, col: Int): Boolean {
        return row in input2.indices && col in input2[row].indices
    }

    fun calculateSides(row: Int, col: Int): Int {
        var sidesCount = 0
        val plantType = input2[row][col]
        for (i in rowDelta.indices) {
            val adjacentRow = row + rowDelta[i]
            val adjacentCol = col + colDelta[i]

            if (!isWithinBounds(adjacentRow, adjacentCol) || input2Copy[adjacentRow][adjacentCol] != plantType) {
                val diagonalRow = row + rowDelta[floorMod(i - 1, 4)]
                val diagonalCol = col + colDelta[floorMod(i - 1, 4)]
                val isStartEdge =
                    !isWithinBounds(diagonalRow, diagonalCol) || input2Copy[diagonalRow][diagonalCol] != plantType

                val cornerRow = adjacentRow + rowDelta[floorMod(i - 1, 4)]
                val cornerCol = adjacentCol + colDelta[floorMod(i - 1, 4)]
                val isConcaveEdge =
                    isWithinBounds(cornerRow, cornerCol) && input2Copy[cornerRow][cornerCol] == plantType

                if (isStartEdge || isConcaveEdge) {
                    sidesCount++
                }
            }
        }
        return sidesCount
    }

    fun computeRegionCost(startRow: Int, startCol: Int): Int {
        var totalArea = 1
        var totalSides = calculateSides(startRow, startCol)

        val plantType = input2[startRow][startCol]
        val queue: LinkedList<Pair<Int, Int>> = LinkedList()
        queue.add(startRow to startCol)
        input2[startRow][startCol] = '#'

        while (queue.isNotEmpty()) {
            val (currentRow, currentCol) = queue.poll()

            for (i in rowDelta.indices) {
                val newRow = currentRow + rowDelta[i]
                val newCol = currentCol + colDelta[i]

                if (isWithinBounds(newRow, newCol) && input2[newRow][newCol] == plantType) {
                    totalArea++
                    totalSides += calculateSides(newRow, newCol)
                    input2[newRow][newCol] = '#'
                    queue.add(newRow to newCol)
                }
            }
        }

        return totalArea * totalSides
    }

    var totalCost = 0

    for (row in input2.indices) {
        for (col in input2[row].indices) {
            if (input2[row][col] != '#') {
                totalCost += computeRegionCost(row, col)
            }
        }
    }

    val input = readInput("Day12")
    part1(input).println()
    println("Part 2: $totalCost")
}
