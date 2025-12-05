fun main() {

    fun part1(freshRanges: List<Pair<Long, Long>>, ingredients: List<Long>): Int {
        return ingredients.count { ingredient ->
            freshRanges.any { ingredient >= it.first && ingredient <= it.second }
        }
    }

    fun part2(freshRanges: List<Pair<Long, Long>>): Long {
        val reducedRange = mutableListOf<Pair<Long, Long>>()

        var tmpRange: Pair<Long, Long>? = null
        freshRanges.forEach { range ->
            if (tmpRange == null) {
                tmpRange = range
            } else {
                if (tmpRange.second >= range.second && tmpRange.second >= range.first) {
                    // element is inside, drop
                } else if (tmpRange.second >= range.first) {
                    tmpRange = tmpRange.first to range.second
                }
                else {
                    reducedRange.add(tmpRange)
                    tmpRange = range
                }
            }
        }
        if (tmpRange != null) {
            reducedRange.add(tmpRange)
        }

        return reducedRange.sumOf { it.second - it.first + 1 }
    }

    val input = readInput("Day05")
    val middle = input.indexOfFirst { it == "" }
    val freshRanges = input.take(middle).map { s -> s.split("-").let { it[0].toLong() to it[1].toLong() } }.sortedBy { it.first }
    val ingredients = input.drop(middle + 1).map { it.toLong() }.sorted()

    part1(freshRanges, ingredients).println() // 874
    part2(freshRanges).println() // 348548952146313
}
