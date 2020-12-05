package days

class Day5 : Day(5) {
    override fun partOne(): Any = pairs.map { it.first * 8 + it.second }.maxOrNull()!!

    private val pairs = inputList.map {
        Pair(
            it.substring(0, 7)
                .replace("F", "0")
                .replace("B", "1")
                .toInt(2),
            it.substring(7)
                .replace("L", "0")
                .replace("R", "1")
                .toInt(2)
        )
    }

    override fun partTwo(): Any {
        var grid = Array(128) { BooleanArray(64) }
        pairs.forEach { grid[it.first][it.second] = true }

        for ((i, row) in grid.withIndex()) {
            if (i == 0 || i >= 127) {
                continue
            }
            for ((j, col) in row.withIndex()) {
                if (j <= 0 || j >= 63 || col) {
                    continue
                }
                var valid = true
                for (r in i - 1..i + 1) {
                    for (c in j - 1..j + 1) {
                        if (r == i && c == j) continue
                        if (!grid[r][c]) {
                            valid = false
                            break
                        }
                    }
                    if (!valid) {
                        break
                    }
                }
                if (valid) {
                    return i * 8 + j
                }
            }
        }
        return -1
    }
}
