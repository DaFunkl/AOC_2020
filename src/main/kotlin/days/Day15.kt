package days

class Day15 : Day(15) {
    private val data = inputString.split(",").map { it.toInt() }

    override fun partOne(): Any = solve(2020)
    override fun partTwo(): Any = solve(30000000)

    private fun solve(n: Int): Any {
        val m = mutableMapOf<Int, MutableList<Int>>()
        var count = 1
        data.forEach { m[it] = mutableListOf(count++) }; count--
        var p = data.last()
        while (count++ < n) {
            p = if (m[p]!!.size == 1) 0
            else m[p]!![m[p]!!.size - 1] - m[p]!![m[p]!!.size - 2]

            if (m.containsKey(p)) m[p]!!.add(count)
            else m[p] = mutableListOf(count)
        }
        return p
    }
}
