fun main() {

    fun evaluateExpression(testValue: Long, numbers: List<Long>): Boolean {
        // Helper function to recursively evaluate all operator combinations
        fun helper(index: Int, current: Long): Boolean {
            if (index == numbers.size) {
                return current == testValue
            }

            // Try addition and multiplication
            return helper(index + 1, current + numbers[index]) ||
                    helper(index + 1, current * numbers[index])
        }

        return helper(1, numbers[0])
    }

    fun part1(input: List<String>): Long {
        var totalCalibrationResult = 0L

        for (line in input) {
            val parts = line.split(":")
            val testValue = parts[0].toLong()
            val numbers = parts[1].trim().split(" ").map { it.toLong() }

            if (evaluateExpression(testValue, numbers)) {
                totalCalibrationResult += testValue
            }
        }
        return totalCalibrationResult
    }

    fun evaluateExpression1(testValue: Long, numbers: List<Long>): Boolean {
        // Helper function to recursively evaluate all operator combinations
        fun helper(index: Int, current: Long): Boolean {
            if (index == numbers.size) {
                return current == testValue
            }

            // Try addition, multiplication, and concatenation
            val nextValue = numbers[index]
            return helper(index + 1, current + nextValue) ||
                    helper(index + 1, current * nextValue) ||
                    helper(index + 1, ("$current$nextValue").toLong())
        }

        return helper(1, numbers[0])
    }

    fun part2(input: List<String>): Long {
        var totalCalibrationResult = 0L

        for (line in input) {
            val parts = line.split(":")
            val testValue = parts[0].toLong()
            val numbers = parts[1].trim().split(" ").map { it.toLong() }

            if (evaluateExpression1(testValue, numbers)) {
                totalCalibrationResult += testValue
            }
        }
        return totalCalibrationResult
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
