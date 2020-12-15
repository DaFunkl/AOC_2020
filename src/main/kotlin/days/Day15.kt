package days

class Day15 : Day(15) {
    private val data = inputString.split(",").map { it.toInt() }

    override fun partOne(): Any = solve(2020)
    override fun partTwo(): Any = solve(30000000)

    private fun solve(n: Int): Any {
        val map = mutableMapOf<Int, MutableList<Int>>()
        var count = 1
        data.forEach { map[it] = mutableListOf(count++) }
        count--
        var previous = data.last()
        while (count++ < n) {
            previous = if (map[previous]!!.size == 1) 0 else {
                val l = map[previous]!!
                l[l.size - 1] - l[l.size - 2]
            }
            if (map.containsKey(previous)) {
                map[previous]!!.add(count)
            } else {
                map[previous] = mutableListOf(count)
            }
        }
        return previous
    }
}
