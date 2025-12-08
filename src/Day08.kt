import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    data class Position(val x: Int, val y: Int, val z: Int) {

        fun distance(position: Position): Double {
            return sqrt(
                (this.x - position.x).toDouble().pow(2) + (this.y - position.y).toDouble()
                    .pow(2) + (this.z - position.z).toDouble().pow(2)
            )
        }

    }

    data class Distance(val box1: Position, val box2: Position, val distance: Double)

    fun part1(elements: List<Position>): Int {
        val initialCircuits = elements.map { setOf(it) }
        val allJunctions = elements
            .flatMapIndexed { i, el ->
                elements.drop(i + 1).map { other ->
                    Distance(el, other, el.distance(other))
                }
            }
            .sortedBy { it.distance }
            .take(1000)

        val result = allJunctions.fold((initialCircuits)) { circuits, newJunction ->
            val firstCircuit = circuits.indexOfFirst { it.contains(newJunction.box1) }
            val secondCircuit = circuits.indexOfFirst { it.contains(newJunction.box2) }
            val newCircuit = circuits[firstCircuit] + circuits[secondCircuit]
            circuits.filterIndexed { i, _ -> i != firstCircuit && i != secondCircuit } + listOf(newCircuit)
        }

        return result.sortedByDescending { it.size }.take(3).map { it.size }.reduce(Int::times)
    }

    fun part2(elements: List<Position>): Int {
        val initialCircuits = elements.map { setOf(it) }
        val allJunctions = elements
            .flatMapIndexed { i, el ->
                elements.drop(i + 1).map { other ->
                    Distance(el, other, el.distance(other))
                }
            }
            .sortedBy { it.distance }

        allJunctions.fold((initialCircuits)) { circuits, newJunction ->
            val firstCircuit = circuits.indexOfFirst { it.contains(newJunction.box1) }
            val secondCircuit = circuits.indexOfFirst { it.contains(newJunction.box2) }
            val newCircuit = circuits[firstCircuit] + circuits[secondCircuit]
            val newCircuits = circuits.filterIndexed { i, _ -> i != firstCircuit && i != secondCircuit } + listOf(newCircuit)

            if (newCircuits.size == 1) {
                return newJunction.box1.x * newJunction.box2.x
            }

            newCircuits
        }

        throw IllegalStateException("It should finish at some point")
    }

    val input = readInput("Day08")
        .map { el -> el.split(",").let { Position(it[0].toInt(), it[1].toInt(), it[2].toInt()) } }

    part1(input).println() // 79560
    part2(input).println() // 31182420
}
