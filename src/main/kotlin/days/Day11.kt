package days

class Day11 : Day(11) {

    val data = inputList.map { it.toCharArray() }

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

    private fun applyRule(list: List<CharArray>, i: Int, j: Int, part: Int) =
        when (part) {
            1 -> applyRule1(list, i, j)
            2 -> applyRule2(list, i, j)
            else -> '.'
        }

    override fun partOne(): Any = solve(1)

    private fun applyRule1(list: List<CharArray>, i: Int, j: Int) = when (list[i][j]) {
        '.' -> '.'
        'L' -> if (countAdjOccupiedSeats1(list, i, j) <= 0) '#' else 'L'
        '#' -> if (countAdjOccupiedSeats1(list, i, j) >= 4) 'L' else '#'
        else -> {
            throw Exception("Unknown Char: ${list[i][j]}")
        }
    }

    private fun countAdjOccupiedSeats1(list: List<CharArray>, i: Int, j: Int): Int {
        var count = 0
        for (x in i - 1..i + 1) {
            if (x < 0 || x >= list.size) continue
            for (y in j - 1..j + 1) {
                if (y < 0 || y >= list[x].size) continue
                if (x == i && y == j) continue
                if (list[x][y] == '#') count++
            }
        }
        return count
    }

    override fun partTwo(): Any = solve(2)

    private fun applyRule2(list: List<CharArray>, i: Int, j: Int) = when (list[i][j]) {
        '.' -> '.'
        'L' -> if (countAdjOccupiedSeats2(list, i, j) <= 0) '#' else 'L'
        '#' -> if (countAdjOccupiedSeats2(list, i, j) >= 5) 'L' else '#'
        else -> {
            throw Exception("Unknown Char: ${list[i][j]}")
        }
    }

    private fun countAdjOccupiedSeats2(list: List<CharArray>, i: Int, j: Int): Int {
        var count = 0
        var x = 1
        var y = 1
        var finished = BooleanArray(8)
        val left = 0
        val right = 1
        val up = 2
        val down = 3
        val leftUp = 4
        val rightUp = 5
        val leftDown = 6
        val rightDown = 7

        while (finished.any { !it }) {
            if (i - x < 0) {
                finished[left] = true
                finished[leftUp] = true
                finished[leftDown] = true
            }
            if (i + x >= list.size) {
                finished[right] = true
                finished[rightDown] = true
                finished[rightUp] = true
            }
            if (j - y < 0) {
                finished[up] = true
                finished[leftUp] = true
                finished[rightUp] = true
            }
            if (j + y >= list[0].size) {
                finished[down] = true
                finished[leftDown] = true
                finished[rightDown] = true
            }
            // @formatter:off
            if (!finished[left]      && list[i - x][  j  ] == '#') count++
            if (!finished[right]     && list[i + x][  j  ] == '#') count++
            if (!finished[up]        && list[  i  ][j - y] == '#') count++
            if (!finished[down]      && list[  i  ][j + y] == '#') count++
            if (!finished[leftUp]    && list[i - x][j - y] == '#') count++
            if (!finished[leftDown]  && list[i - x][j + y] == '#') count++
            if (!finished[rightUp]   && list[i + x][j - y] == '#') count++
            if (!finished[rightDown] && list[i + x][j + y] == '#') count++

            finished[left]      = finished[left]      ||  list[i - x][  j  ] != '.'
            finished[right]     = finished[right]     ||  list[i + x][  j  ] != '.'
            finished[up]        = finished[up]        ||  list[  i  ][j - y] != '.'
            finished[down]      = finished[down]      ||  list[  i  ][j + y] != '.'
            finished[leftUp]    = finished[leftUp]    ||  list[i - x][j - y] != '.'
            finished[leftDown]  = finished[leftDown]  ||  list[i - x][j + y] != '.'
            finished[rightUp]   = finished[rightUp]   ||  list[i + x][j - y] != '.'
            finished[rightDown] = finished[rightDown] ||  list[i + x][j + y] != '.'
            // @formatter:on
            x++
            y++
        }
        return count
    }

}
