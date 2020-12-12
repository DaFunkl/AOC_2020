package days


class Day12 : Day(12) {

    val data: List<Pair<String, Int>> = inputList.map { Pair(it.substring(0, 1), it.substring(1).toInt()) }

    override fun partOne(): Any {
        var position = mutableListOf(0, 0, 1, 0)
        data.forEach {
            when (it.first) {
                "N" -> position[1] -= it.second
                "S" -> position[1] += it.second
                "E" -> position[0] += it.second
                "W" -> position[0] -= it.second
                "L", "R" -> position = turn(position, it)
                "F" -> {
                    position[0] += position[2] * it.second
                    position[1] += position[3] * it.second
                }
                else -> throw Exception("Unknown Action: ${it.first} -> ${it.second}")
            }
        }
        return Math.abs(position[0]) + Math.abs(position[1])
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

    override fun partTwo(): Any {
        var position = mutableListOf(10, -1, 1, 0)
        data.forEach {
            when (it.first) {
                "N" -> position[1] -= it.second * 2
                "S" -> position[1] += it.second* 2
                "E" -> position[0] += it.second* 2
                "W" -> position[0] -= it.second* 2
                "L", "R" -> position = turn(position, it)
                "F" -> {
                    position[0] += position[2] * it.second* 2
                    position[1] += position[3] * it.second* 2
                }
                else -> throw Exception("Unknown Action: ${it.first} -> ${it.second}")
            }
        }
        return Math.abs(position[0]) + Math.abs(position[1])
    }
}
