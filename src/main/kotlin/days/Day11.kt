package days

class Day11 : Day(11) {

    val data = inputList.map { it.toCharArray() }

    override fun partOne(): Any = solve(1)

    override fun partTwo(): Any = solve(2)

    fun solve(part: Int): Any {
        var currentState = ArrayList(data)
        while (true) {
            var newState = ArrayList<CharArray>()
            for ((i, r) in currentState.withIndex()) {
                newState.add(CharArray(currentState[i].size))
                for ((j, _) in r.withIndex()) {
                    newState[i][j] = applyRule(currentState, i, j, part)
                }
            }
            if (isEqual(currentState, newState)) {
                break
            }
            currentState = newState
        }
        return currentState.sumBy { it.count { it == '#' } }
    }

    fun isEqual(state1: List<CharArray>, state2: List<CharArray>): Boolean {
        for ((i, r) in state1.withIndex()) {
            for ((j, c) in r.withIndex()) {
                if (state2[i][j] != c) return false
            }
        }
        return true;
    }

    private fun applyRule(list: List<CharArray>, i: Int, j: Int, part: Int) = when (part) {
        1 -> fetchChar(list, i, j, 1, 4)
        2 -> fetchChar(list, i, j, Int.MAX_VALUE, 5)
        else -> '.'
    }

    private fun fetchChar(list: List<CharArray>, i: Int, j: Int, cap: Int, rule: Int) = when (list[i][j]) {
        '.' -> '.'
        'L' -> if (countAdjOccupiedSeats(list, i, j, cap) <= 0) '#' else 'L'
        '#' -> if (countAdjOccupiedSeats(list, i, j, cap) >= rule) 'L' else '#'
        else -> {
            throw Exception("Unknown Char: ${list[i][j]}")
        }
    }

    private fun countAdjOccupiedSeats(list: List<CharArray>, i: Int, j: Int, cap: Int = Int.MAX_VALUE): Int {
        var count = 0
        var finished = BooleanArray(directions.size)
        var r = 1
        while (finished.any { !it } && r <= cap) {
            for ((idx, p) in directions.withIndex()) {
                if (finished[idx]) continue

                val coordinate = Pair(i + r * p.first, j + r * p.second)
                if (!(coordinate.first in list.indices && coordinate.second in list[0].indices)) {
                    finished[idx] = true
                }

                if (!finished[idx] && list[coordinate.first][coordinate.second] == '#') count++

                finished[idx] = finished[idx] || list[coordinate.first][coordinate.second] != '.'
            }
            r++
        }
        return count
    }

    val directions = listOf(
        Pair(-1, +0), // left
        Pair(+1, +0), // right
        Pair(+0, -1), // up
        Pair(+0, +1), // down
        Pair(-1, -1), // left up
        Pair(-1, +1), // left down
        Pair(+1, -1), // right up
        Pair(+1, +1) // right down
    )
}
