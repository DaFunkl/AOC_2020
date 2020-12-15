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
            if (visited[p]) {
                val np = (idx-1) - num[p]
                num[p] = (idx-1)
                p = np
            } else {
                visited[p] = true
                num[p] = idx - 1
                p = 0
            }
        } while (idx++ < n)
        return p
    }
}
