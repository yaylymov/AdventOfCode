fun main() {

    fun blink(stones: MutableList<Long>): MutableList<Long> {
        val newStones = mutableListOf<Long>()
        for (stone in stones) {
            when {
                stone == 0L -> newStones.add(1L)
                stone.toString().length % 2 == 0 -> {
                    val digits = stone.toString()
                    val mid = digits.length / 2
                    val left = digits.substring(0, mid).toLong()
                    val right = digits.substring(mid).toLong()
                    newStones.add(left)
                    newStones.add(right)
                }
                else -> newStones.add(stone * 2024)
            }
        }
        return newStones
    }

    fun part1(input: List<String>, blinks: Int): Int {
        var stones = input[0].split(" ").map { it.toLong() }.toMutableList()

        repeat(blinks) {
            stones = blink(stones)
        }

        return stones.size
    }


    fun calculateStoneCount(stones: List<Long>, blinks: Int): Long {
        var currentStoneCounts = stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        repeat(blinks) {
            val nextStoneCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in currentStoneCounts) {
                when {
                    stone == 0L -> {
                        nextStoneCounts[1L] = nextStoneCounts.getOrDefault(1L, 0L) + count
                    }

                    stone.toString().length % 2 == 0 -> {
                        val digits = stone.toString()
                        val mid = digits.length / 2
                        val left = digits.substring(0, mid).toLong()
                        val right = digits.substring(mid).toLong()
                        nextStoneCounts[left] = nextStoneCounts.getOrDefault(left, 0L) + count
                        nextStoneCounts[right] = nextStoneCounts.getOrDefault(right, 0L) + count
                    }

                    else -> {
                        val newStone = stone * 2024
                        nextStoneCounts[newStone] = nextStoneCounts.getOrDefault(newStone, 0L) + count
                    }
                }
            }
            currentStoneCounts = nextStoneCounts
        }
        return currentStoneCounts.values.sum()
    }

    fun part2(input: List<String>, blinks: Int): Long {
        val stones = input[0].split(" ").map { it.toLong() }
        return calculateStoneCount(stones, blinks)
    }

    val input = readInput("Day11")
    part1(input, 25).println()
    part2(input, 75).println()
}
