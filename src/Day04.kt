fun main() {
    val siblings = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    fun canBeRemoved(grid: List<List<Int>>, x: Int, y: Int): Boolean {
        return siblings.count { (i, j) ->
            grid.getOrNull(x + i)?.getOrNull(j + y) == 1
        } < 4
    }

    fun part1(grid: List<List<Int>>): Int {
        return grid
            .flatMapIndexed { i, line ->
                line.filterIndexed { j, item ->
                    item == 1 && canBeRemoved(grid, i, j)
                }
            }
            .count()
    }

    fun part2(input: List<List<Int>>): Int {
        var changed = true
        var removed = 0
        val grid = input.map { it.toMutableList() }
        while (changed) {
            changed = false
            grid.forEachIndexed { i, line ->
                line.forEachIndexed { j, item ->
                    if (item == 1 && canBeRemoved(grid, i, j)) {
                        grid[i][j] = 0
                        changed = true
                        removed++
                    }
                }
            }
        }
        return removed
    }

    val input = readInput("Day04")
        .map { line -> line.toList().map { if (it == '@') 1 else 0 } }

    part1(input).println() // 1372
    part2(input).println() // 7922
}
