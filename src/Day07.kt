fun main() {

    fun part1(input: List<String>): Int {
        val result = List(input.size) { MutableList(input.first().length) { '.' } }
        var counter = 0

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, el ->
                when (el) {
                    'S' -> result[i][j] = '|'
                    '^' -> {
                        if (result.getOrNull(i - 1)?.getOrNull(j) == '|') {
                            result[i + 1][j - 1] = '|'
                            result[i + 1][j + 1] = '|'

                            counter++
                        }
                    }

                    '.' -> {
                        if (result.getOrNull(i - 1)?.getOrNull(j) == '|') {
                            result[i][j] = '|'
                        }
                    }
                }
            }
        }

        return counter
    }

    fun part2(input: List<String>): Long {
        val result = List(input.size) { MutableList(input.first().length) { 0L } }
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, el ->
                when (el) {
                    'S' -> {
                        result[i][j] = 1
                    }

                    '^' -> {
                        result[i][j - 1] += result[i - 1][j]
                        result[i][j + 1] += result[i - 1][j]
                    }

                    '.' -> {
                        if (i > 0) {
                            result[i][j] += result[i - 1][j]
                        }
                    }
                }
            }
        }

        return result.last().sum()
    }

    val input = readInput("Day07")

    part1(input).println() // 1640
    part2(input.filterIndexed { i, _ -> i % 2 == 0 }).println() // 40999072541589
}
