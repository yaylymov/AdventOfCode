fun main() {

    fun parseMap(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val antennaMap = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        for (y in input.indices) {
            for (x in input[y].indices) {
                val char = input[y][x]
                if (char.isLetterOrDigit()) {
                    antennaMap.computeIfAbsent(char) { mutableListOf() }.add(Pair(x, y))
                }
            }
        }
        return antennaMap
    }

    fun isAligned(coord: Pair<Int, Int>, antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>): Boolean {
        val (x1, y1) = antenna1
        val (x2, y2) = antenna2
        val (xc, yc) = coord

        val dx = x2 - x1
        val dy = y2 - y1
        val vcx = xc - x1
        val vcy = yc - y1

        return dx != 0 && vcx * dy == vcy * dx
    }

    fun part1(input: List<String>): Int {
        val antennaMap = parseMap(input)
        val mapWidth = input[0].length
        val mapHeight = input.size

        val allAntinodes = mutableSetOf<Pair<Int, Int>>()

        for ((_, antennas) in antennaMap) {
            for (i in antennas.indices) {
                for (j in i + 1 until antennas.size) {
                    val (x1, y1) = antennas[i]
                    val (x2, y2) = antennas[j]

                    val dx = x2 - x1
                    val dy = y2 - y1

                    val mx = x1 - dx
                    val my = y1 - dy
                    val nx = x2 + dx
                    val ny = y2 + dy

                    if (mx in 0 until mapWidth && my in 0 until mapHeight) {
                        allAntinodes.add(Pair(mx, my))
                    }
                    if (nx in 0 until mapWidth && ny in 0 until mapHeight) {
                        allAntinodes.add(Pair(nx, ny))
                    }
                }
            }
        }

        return allAntinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennaMap = parseMap(input)
        val entireMap = mutableSetOf<Pair<Int, Int>>()

        for (y in input.indices) {
            for (x in input[y].indices) {
                entireMap.add(Pair(x, y))
            }
        }

        val antennaPairs = antennaMap.values.flatMap { antennas ->
            antennas.indices.flatMap { i ->
                (i + 1 until antennas.size).map { j ->
                    antennas[i] to antennas[j]
                }
            }
        }

        return entireMap.count { coord ->
            antennaPairs.any { (antenna1, antenna2) ->
                isAligned(coord, antenna1, antenna2)
            }
        }
    }

    val input = readInput("Day08")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
