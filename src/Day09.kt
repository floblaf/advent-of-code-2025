import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    // X axes = horizontal
    // Y axes = vertical

    data class Position(val x: Int, val y: Int) {
        fun area(position: Position): Long {
            return (abs(position.x - x) + 1L) * (abs(position.y - y) + 1L)
        }
    }

    data class Segment(val start: Position, val end: Position) {
        init {
            require (start.x == end.x || start.y == end.y) {
                "Segment should all be horizontal or vertical"
            }
        }


        fun cross(other: Segment): Boolean {
            // one point in common -> they are not crossing each other
            if (start == other.start || start == other.end || end == other.start || end == other.end) {
                return false
            }

            if (other.start.x == other.end.x) {
                if (other.start.x >= min(start.x, end.x) && other.start.x <= max(start.x, end.x)) {
                    return start.y >= min(other.start.y, other.end.y) && start.y <= max(other.start.y, other.end.y)
                }
            } else if (other.start.y == other.end.y) {
                if (other.start.y >= min(start.y, end.y) && other.start.y <= max(start.y, end.y)) {
                    return start.x >= min(other.start.x, other.end.x) && start.x <= max(other.start.x, other.end.x)
                }
            } else {
                error("Segment should be orthogonal")
            }

            return false
        }
    }

    fun noCrossing(
        first: Position,
        second: Position,
        horizontalSegments: List<Segment>,
        verticalSegments: List<Segment>
    ): Boolean {
        val xMin = min(first.x, second.x) + 1
        val yMin = min( first.y, second.y) + 1
        val xMax = max( first.x, second.x) - 1
        val yMax = max(first.y, second.y) - 1

        val top = Segment(Position(xMin, yMax), Position(xMax, yMax))
        val bottom = Segment(Position(xMin, yMin), Position(xMax, yMin))
        val right = Segment(Position(xMax, yMin), Position(xMax, yMax))
        val left = Segment(Position(xMin, yMin), Position(xMin, yMax))

        horizontalSegments.forEach { hSegment ->
            if (left.cross(hSegment)) {
                return false
            }

            if (right.cross(hSegment)) {
                return false
            }
        }

        verticalSegments.forEach { vSegment ->
            if (top.cross(vSegment)) {
                return false
            }

            if (bottom.cross(vSegment)) {
                return false
            }
        }
        return true
    }


    fun part1(redTiles: List<Position>): Long {
        return redTiles.flatMapIndexed { i, tile ->
            redTiles.drop(i + 1).map { other ->
                tile.area(other)
            }
        }.max()
    }

    fun part2(redTiles: List<Position>): Long {
        val horizontalSegments = mutableListOf<Segment>()
        val verticalSegments = mutableListOf<Segment>()
        redTiles.forEachIndexed { i, tile ->
            val next = redTiles.getOrNull(i + 1) ?: redTiles.first()
            if (tile.x == next.x) {
                verticalSegments.add(
                    if (tile.y < next.y) {
                        Segment(tile, next)
                    } else {
                        Segment(next, tile)
                    }
                )
            } else if (tile.y == next.y) {
                horizontalSegments.add(
                    if (tile.x < next.x) {
                        Segment(tile, next)
                    } else {
                        Segment(next, tile)
                    }
                )
            } else {
                error("Data corrupted")
            }
        }

        return redTiles
            .flatMapIndexed { i, tile ->
                redTiles.drop(i + 1).map { other ->
                    tile to other
                }
            }
            .fold(0L) { max, (first, second) ->
                val area = first.area(second)
                if (area > max && noCrossing(first, second, horizontalSegments, verticalSegments)) {
                    area
                } else {
                    max
                }
            }
    }

    val input = readInput("Day09")
        .map { el -> el.split(",").let { Position(it[0].toInt(), it[1].toInt()) } }

    part1(input).println() // 4741848414
    part2(input).println() // 1508918480
}