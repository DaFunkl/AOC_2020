package days

class Day15 : Day(15) {
    private val data = inputString.split(",").map { it.toInt() }

    override fun partOne(): Any = solve(2020)
    override fun partTwo(): Any = solve(30000000)

    private fun solve(n: Int): Any {
        val num = IntArray(n + 1)
        val visited = BooleanArray(n + 1)
        var idx = 1
        data.forEach {
            num[it] = idx++
            visited[it] = true
        }
        var p = data.last()
        do {
            val pp = p
            p = if (visited[pp]) idx - 1 - num[pp] else 0
            visited[pp] = true
            num[pp] = idx - 1
        } while (idx++ < n)
        return p
    }
}
