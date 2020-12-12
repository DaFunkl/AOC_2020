package days

import kotlin.math.abs


class Day12 : Day(12) {
    private val data: List<Pair<String, Int>> = inputList.map { Pair(it.substring(0, 1), it.substring(1).toInt()) }

    override fun partOne(): Any = solve(1, 0, 0)

    override fun partTwo(): Any = solve(10, -1, 2)

    private fun solve(wpx: Int, wpy: Int, compassIdx: Int): Any {
        var position = mutableListOf(0, 0, wpx, wpy)
        data.forEach {
            when (it.first) {
                "N" -> position[compassIdx + 1] -= it.second
                "S" -> position[compassIdx + 1] += it.second
                "E" -> position[compassIdx] += it.second
                "W" -> position[compassIdx] -= it.second
                "L", "R" -> position = turn(position, it)
                "F" -> {
                    position[0] += position[2] * it.second
                    position[1] += position[3] * it.second
                }
                else -> throw Exception("Unknown Action: ${it.first} -> ${it.second}")
            }
        }
        return abs(position[0]) + abs(position[1])
    }

    private fun turn(position: MutableList<Int>, action: Pair<String, Int>): MutableList<Int> {
        val mult = when (action.first) {
            "L" -> Pair(-1, 1)
            "R" -> Pair(1, -1)
            else -> throw Exception("Unknown Action: ${action.first} -> ${action.second}")
        }
        for (i in 0 until (action.second / 90)) {
            val temp = position[2] * mult.first
            position[2] = position[3] * mult.second
            position[3] = temp
        }
        return position
    }
}
