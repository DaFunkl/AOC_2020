package days

class Day15 : Day(15) {
    private val data = inputString.split(",").map { it.toInt() }

    override fun partOne(): Any = solve(2020)
    override fun partTwo(): Any = solve(30000000)

    private fun solve(n: Int): Any {
        val num = IntArray(n + 1)
        var idx = 1
        data.forEach {num[it] = idx++}
        var p = data.last()
        do {
            val pp = p
            p = if (num[pp] != 0) idx - 1 - num[pp] else 0
            num[pp] = idx - 1
        } while (idx++ < n)
        return p
    }
}
