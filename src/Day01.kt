fun main() {
    fun part1(instructions: List<Pair<String, Int>>): Int {
        return instructions
            .runningFold(50) { current, (direction, clicks) ->
                when (direction) {
                    "L" -> (current + clicks) % 100
                    "R" -> (current - clicks) % 100
                    else -> throw Exception("Should not happen")
                }
            }
            .count { it == 0 }
    }

    fun part2(instructions: List<Pair<String, Int>>): Int {
        return instructions
            .fold(Pair(50, 0)) { (current, zeros), (direction, clicks) ->
                val tmp = when (direction) {
                    "L" -> (current + clicks)
                    "R" -> (current - clicks)
                    else -> throw Exception("Should not happen")
                }

                when {
                    tmp == 0 -> 0 to zeros + 1
                    tmp >= 100 -> tmp.mod(100) to (zeros + (tmp / 100))
                    tmp < 0 -> tmp.mod(100) to (zeros - (tmp / 100) + if (current != 0) 1 else 0)
                    else -> tmp to zeros
                }
            }
            .second

    }

    val input = readInput("Day01")
    val instructions = input.map { it.substring(0, 1) to it.substring(1).toInt() }

    part1(instructions).println() // 1066
    part2(instructions).println() // 6223
}
