fun main() {
    fun getHalf(i: Long, includeMiddleDigit: Boolean): Long {
        return i.toString()
            .let { it.substring(0, it.length / 2 + if (includeMiddleDigit) 1 else 0).ifBlank { "0" }.toLong() }
    }

    fun part1(ranges: List<Pair<Long, Long>>): Long {
        return ranges.flatMap { pair ->
            val start = getHalf(pair.first, false)
            val end = getHalf(pair.second, true)
            (start..end).mapNotNull {
                val double = (it.toString() + it.toString()).toLong()
                if (double in pair.first..pair.second) {
                    double
                } else {
                    null
                }
            }
        }.sum()
    }

    fun isRepeating(number: String): Boolean {
        (1..(number.length / 2)).forEach { length ->
            if (number.length % length == 0) {
                val chunk = number.chunked(length).toSet()
                if (chunk.size == 1) {
                    return true
                }
            }
        }
        return false
    }

    fun part2(ranges: List<Pair<Long, Long>>): Long {
        return ranges.flatMap { pair ->
                (pair.first..pair.second).filter {
                    isRepeating(it.toString())
                }
            }.sum()
    }

    val input = readInput("Day02")
    val ranges = input[0].split(",").map { range ->
            range.split("-").let {
                it[0].toLong() to it[1].toLong()
            }
        }

    part1(ranges).println() // 24157613387
    part2(ranges).println() // 33832678380
}
