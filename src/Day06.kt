fun main() {

    fun compute(operations: List<Pair<Operator, List<Long>>>): Long {
        return operations.sumOf { operation ->
            operation.second.fold(if (operation.first == Operator.Multiply) 1L else 0L) { acc, value ->
                if (operation.first == Operator.Multiply) {
                    acc * value
                } else {
                    acc + value
                }
            }
        }
    }

    fun part1(): Long {
        val input = readInput("Day06").map {
            it.trim().replace("\\s+".toRegex(), " ").split(" ")
        }

        val operations = input.last().mapIndexed { i, operator ->
            (if (operator == "*") Operator.Multiply else Operator.Add) to mutableListOf<Long>()
        }

        input.dropLast(1).forEach { line ->
            line.forEachIndexed { i, value ->
                operations[i].second.add(value.toLong())
            }
        }

        return compute(operations)
    }

    fun part2(): Long {
        val operations = mutableListOf<Pair<Operator, MutableList<Long>>>()

        val input = readInput("Day06")
        val lineSize = input.first().length
        val lineCount = input.size

        (0..lineSize - 1).map { i ->
            var tmp = ""
            for (j in 0..lineCount-1) {
                tmp += input[j][i]
            }
            tmp
        }.map { it.trim().replace("\\s+".toRegex(), "") }
            .forEach {
                if (it.isEmpty()) {
                    // nothing
                } else if (it.last() in listOf('*', '+')) {
                    operations.add((if (it.last() == '*') Operator.Multiply else Operator.Add) to mutableListOf(it.dropLast(1).toLong()))
                } else {
                    operations.last().second.add(it.toLong())
                }
            }

        return compute(operations)
    }

    part1().println() // 5335495999141
    part2().println() // 10142723156431
}

enum class Operator {
    Multiply, Add
}
