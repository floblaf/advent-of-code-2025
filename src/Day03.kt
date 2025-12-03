fun main() {
    fun part1(banks: List<List<Int>>): Int {
        return banks.sumOf { bank ->
            val max = bank.dropLast(1).withIndex().maxBy { it.value }
            val second = bank.drop(max.index + 1).max()
            max.value * 10 + second
        }
    }

    fun part2(banks: List<List<Int>>): Long {
        return banks.sumOf { bank ->
            var newBank = bank
            (11.downTo(0))
                .map { remaining ->
                    val max = newBank.dropLast(remaining).withIndex().maxBy { it.value }
                    newBank = newBank.drop(max.index + 1)
                    max.value
                }
                .joinToString("").toLong()
        }
    }

    val input = readInput("Day03")
        .map { bank -> bank.toList().map { it.digitToInt() } }

    part1(input).println() // 17435
    part2(input).println() // 172886048065379
}
